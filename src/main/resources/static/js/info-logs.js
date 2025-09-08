class InfoLogs {
    constructor() {
        this.updateTask = null;
        this.logTypeTemplates = new Map();
        this.logRowTemplate = null;
        this.lastEntryId = Number.MAX_SAFE_INTEGER;
        this.scrollingDone = false;
        this.initListers();
    }

    initListers() {
        document.addEventListener('DOMContentLoaded', () => {
            document.getElementById('info-logs-content-table').addEventListener('scroll', async e => {
                const scroll = e.target.scrollHeight;
                const top = e.target.scrollTop;
                if (scroll + top - 62 < e.target.offsetHeight) {
                    e.preventDefault();
                    await this.fetchFutureLogs();
                    e.target.scrollTop = top + (e.target.scrollHeight - scroll);
                }
            });
            const logsTable = document.getElementById('info-logs-content-table');
            logsTable.innerHTML += '<div class="logs-loader-icon"></div>';
            this.fetchFutureLogs();
        });
    }

    async getLogTypeTemplate(logType) {
        logType = logType.toLowerCase();
        const self = this;
		return await mainCore.getLoadableResource(
				`/templates/info/logs/types/${encodeURIComponent(logType)}.html`,
				() => self.logTypeTemplates.get(logType),
				e => self.logTypeTemplates.set(logType, e)
		);
    }

    async getLogRowTemplate() {
    	const self = this;
    	return await mainCore.getLoadableResource(
    			`/templates/info/logs/entry.html`,
    			() => self.logRowTemplate,
    			e => self.logRowTemplate = e
    	);
    }

    async fetchFutureLogs() {
		if (this.updateTask == null) {
			const loader = jQuery('.logs-loader-icon');
			if (loader.length > 0) {
				try {
					this.updateTask = jQuery.get(`/api/logs/user/${encodeURIComponent(document.body.dataset.userId)}?lastId=${encodeURIComponent(this.lastEntryId)}`);
					const [logs, rowTemplate] = await Promise.all([this.updateTask, this.getLogRowTemplate()]);
					if (logs.length > 0) {
						await Promise.all(
							logs.map(
								async log => mainCore.replaceTextInTemplate(
									 rowTemplate.replaceAll('{{action}}', await this.getLogTypeTemplate(log.type)), {
										 date: new Intl.DateTimeFormat(undefined, {timeStyle: "medium", dateStyle: "short"}).format(new Date(log.time)),
										 type: log.type.toLowerCase(),
										 message: log.message,
										 command: '/' + log.command + (log.arguments ? ' ' + log.arguments : ''),
										 server: log.server,
										 reason: log.reason,
										 targetServer: log.targetServer
									 })))
							.then(res => res.join(''))
							.then(jQuery)
							.then(e => e.insertBefore(loader));
						this.lastEntryId = Math.min(...logs.map(e => e.id));
					} else {
						loader.remove();
						this.scrollingDone = true;
					}
				} finally {
					this.updateTask = null;
				}
			}
		}
	}
};
const infoLogs = new InfoLogs();
