class ModChangeLogs {
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
            document.getElementById('info-logs-content-table').addEventListener('scroll', (e) => {
                if (e.target.scrollHeight + e.target.scrollTop - 62 < e.target.offsetHeight) {
                    this.fetchFutureLogs();
                }
            });
            const logsTable = document.getElementById('info-logs-content-table');
            logsTable.innerHTML += '<div class="logs-loader-icon"></div>';
            this.fetchFutureLogs();
        });
    }


    async getLogTypeTemplate(logType) {
        logType = logType.toLowerCase();
        if (this.logTypeTemplates.has(logType)) {
            // still loading
            if (this.logTypeTemplates.get(logType) instanceof Promise) {
                return await this.logTypeTemplates.get(logType);
            }
            // loaded
            return this.logTypeTemplates.get(logType);
        }
        // not present
        this.logTypeTemplates.set(logType, jQuery.get(`/templates/moderators/logs/types/${encodeURIComponent(logType)}.html`)
                .then(e => {
                    this.logTypeTemplates.set(logType, e);
                    return e;
                }));
        return this.logTypeTemplates.get(logType);
    }


    async getLogRowTemplate() {
        if (this.logRowTemplate !== null) {
            // still loading
            if (this.logRowTemplate instanceof Promise) {
                return await this.logRowTemplate;
            }
            // loaded
            return this.logRowTemplate;
        }
        // not present
        return this.logRowTemplate = jQuery.get(`/templates/moderators/logs/entry.html`)
                .then(e => {
                    this.logRowTemplate = e;
                    return e;
                });
    }


    async fetchFutureLogs() {
        if (this.updateTask == null) {
            const loader = jQuery('.logs-loader-icon');
            if (loader.length > 0) {
                try {
                    this.updateTask = jQuery.get(`/api/moderation/logs?lastId=${encodeURIComponent(this.lastEntryId)}`);
                    const [logs, rowTemplate] = await Promise.all([this.updateTask, this.getLogRowTemplate()]);
                    if (logs.length > 0) {
                        Promise.all(
                            logs.map(
                                async log => mainCore.replaceTextInTemplate(
                                     rowTemplate.replaceAll('{{action}}', await this.getLogTypeTemplate(log.type)), {
                                         date: new Intl.DateTimeFormat().format(new Date(log.time)),
                                         type: log.type,
                                         userName: await mainCore.getUsername(log.user.uuid),
                                         uuid: log.user.uuid,
                                         comment: log.description,
                                         groupFromName: log.groupFrom?.name,
                                         groupFromColor: log.groupFrom?.color,
                                         groupToName: log.groupTo?.name,
                                         groupToColor: log.groupTo?.color
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
const modChangeLogs = new ModChangeLogs();
