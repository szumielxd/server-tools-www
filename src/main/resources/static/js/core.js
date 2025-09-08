class MainCore {

    constructor() {
        this.usernames = new Map();
    }

    async getUsername(uuid) {
        uuid = uuid.toLowerCase();
        if (this.usernames.has(uuid)) {
            // still loading
            if (this.usernames.get(uuid) instanceof Promise) {
                return await this.usernames.get(uuid);
            }
            // loaded
            return this.usernames.get(uuid);
        }
        // not present
        this.usernames.set(uuid, jQuery.get(`/api/auth/user/${uuid}/name`)
                .then(e => {
                    this.usernames.set(uuid, e);
                    return e;
                }));
        return this.usernames.get(uuid);
    }

	changeTextContents(element, pattern, replacer) {
		const self = this;
		element.childNodes.forEach(node => {
			if (node.nodeType === Node.TEXT_NODE) {
				node.nodeValue = node.nodeValue.replaceAll(pattern, replacer);
			} else {
				self.changeTextContents(node, pattern, replacer);
			}
		})
	}

	changeTextContentsToNode(element, pattern, replacer) {
		const self = this;
		for (let i = 0; i < element.childNodes.length; i++) {
			const node = element.childNodes[i];
			if (node.nodeType === Node.TEXT_NODE) {
				const regexp = new RegExp(pattern.source, pattern.flags);
				const str = node.nodeValue;
				let match;
				let lastIndex = 0;
				if ((match = regexp.exec(str)) !== null) {
					let nodes = [];
					
					while (match !== null) {
						nodes.push(document.createTextNode(str.substring(lastIndex, match.index)));
						nodes.push(replacer(match[0], match[1]) ?? document.createTextNode(""));
						lastIndex = regexp.lastIndex;
						match = regexp.exec(str);
					}
					nodes.push(document.createTextNode(str.substring(lastIndex)));

					i += nodes.length - 1;
					nodes.forEach(e => element.insertBefore(e, node));
					element.removeChild(node);

				}
			} else {
				self.changeTextContentsToNode(node, pattern, replacer);
			}
		}
	}

	createElementWithClasses(type, ...classes) {
		const element = document.createElement(type);
		element.classList.add(...classes);
		return element;
	}

	formatDate(time, showSeconds = true) {
		const date = new Date(time*1000);
		const day = ('0' + date.getDate()).slice(-2);
		const month = ('0' + (date.getMonth() + 1)).slice(-2);
		const year = date.getFullYear();
		const hours = ('0' + date.getHours()).slice(-2);
		const minutes = ('0' + date.getMinutes()).slice(-2);
		const seconds = ('0' + date.getSeconds()).slice(-2);
		let result = `${day}.${month}.${year} ${hours}:${minutes}`;
		if (showSeconds) {
			result += `:${seconds}`;
		}
		return result;
	}

	replaceTextInTemplate(template, replacements) {
		return template.replaceAll(/\{\{([a-z\d\-_]+)\}\}/ig, (g, r) => {
			if (r in replacements) {
				return this.escapeHTML(replacements[r]);
			}
			return g;
		});
	}

	escapeHTML(text) {
		return (new String(text)).replaceAll(/&/g, '&amp;')
			.replaceAll(/</g, '&lt;')
			.replaceAll(/>/g, '&gt;')
			.replaceAll(/"/g, '&quot;')
			.replaceAll(/'/g, '&#39;');
	}

	unescapeHTML(text) {
		return (new String(text)).replaceAll(/&amp;/g, '&')
			.replaceAll(/&lt;/g, '<')
			.replaceAll(/&gt;/g, '>')
			.replaceAll(/&quot;/g, '"')
			.replaceAll(/&#39;/g, "'");
	}

	urlRequestGet(uri) {
		const request = new XMLHttpRequest();
		request.open('GET', uri, true);
		const promise = new Promise((resolve, reject) => {
			request.onload = function() {
				if (request.status === 200) {
					resolve(request.responseText);
				} else {
					reject();
				}
			}
		});
		request.send();
		return promise;
	}

	async getLoadableResource(url, getter, setter) {
		let result = getter();
		if (result !== undefined && result !== null) {
			// still loading
			if (result instanceof Promise) {
				return await result;
			}
			// loaded
			return result;
		}
		// not present
		result = jQuery.get(url)
				.then(e => {
					setter(e);
					return e;
				});
		setter(result);
		return await result;
	}

};
const mainCore = new MainCore();

function processCloseNotificationClick(event) {
	closeNotification(jQuery(event.target).parents('.notification-all'));
}

function closeNotification(parent) {
	parent.removeClass('active');
	parent.find('[stage]').attr('stage', null);
	parent.find('[modify]').attr('modify', null);
}

jQuery(document).ready(function() {
	jQuery('.notification-all').on('click', function(event) {
		if (event.target == this) {
			closeNotification(jQuery(event.target));
		}
	});
});
