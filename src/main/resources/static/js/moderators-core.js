function moderatorDragstartHandler(event) {
	const target = jQuery(event.target);
	const data = getModeratorFromButton(target);
	const frozen = target.attr('frozen');
	event.dataTransfer.setData("application/moderation-user", JSON.stringify(data));
	event.dataTransfer.setData("text", data.user.name);
	jQuery('.info-list-content-entries').each((i, e) => {
		const el = jQuery(e);
		if (parseInt(el.attr('group-id')) !== data.group.id) {
			const hover = el.children('.info-list-content-entries-drop-hover');
			hover.addClass('info-list-content-entries-drop-hover-visible');
			if (parseInt(el.attr('group-priority')) >= data.group.weight) {
				hover.attr('action', 'promotion');
			} else {
				hover.attr('action', 'demotion');
			}
		}
	});
	jQuery('#info-list').addClass('drag-active');
	jQuery('#info-list-actions').attr('frozen', frozen);
}

function getModeratorFromButton(target) {
	const group = target.parents('.info-list-content-entries').first();
	console.log(target[0]);
	console.log(target.attr('user-name'));
	return data = {
		user: {
			uuid: target.attr('user-uuid'),
			name: target.attr('user-name')
		},
		group: {
			id: parseInt(group.attr('group-id')),
			name: group.attr('group-name'),
			weight: parseInt(group.attr('group-priority')),
			color: group.attr('group-color')
		}
	};
}

function moderatorDragendHandler(event) {
	if (event.dataTransfer.types.includes("application/moderation-user")) {
		jQuery('.info-list-content-entries').each((i, e) => {
			const el = jQuery(e);
			const hover = el.children('.info-list-content-entries-drop-hover');
			hover.removeClass('info-list-content-entries-drop-hover-visible');
		});
		jQuery('#info-list').removeClass('drag-active');
		jQuery('.drag-hover').removeClass('drag-hover');
	}
}

function moderatorDragoverHandler(event) {
	if (event.dataTransfer.types.includes("application/moderation-user")) {
		event.preventDefault();
	}
}

function moderatorDragenterHandler(event) {
	if (event.dataTransfer.types.includes("application/moderation-user")) {
		jQuery(event.target).parent().addClass('drag-hover');
	}
}

function moderatorDragleaveHandler(event) {
	if (event.dataTransfer.types.includes("application/moderation-user")) {
		jQuery(event.target).parent().removeClass('drag-hover');
	}
}

function moderatorDropHandler(event) {
	if (event.dataTransfer.types.includes("application/moderation-user")) {
		const data = JSON.parse(event.dataTransfer.getData("application/moderation-user"));
		const target = jQuery(event.target);
		const group = target.parents('.info-list-content-entries').first();
		const groupTo = {
			id: parseInt(group.attr('group-id')),
			name: group.attr('group-name'),
			weight: parseInt(group.attr('group-priority')),
			color: group.attr('group-color'),
		};
		const groupFrom = data.group;
		const notification = jQuery('#notification-' + (groupTo.weight >= groupFrom.weight ? 'promotion' : 'demotion'));
		notification.attr('stage', 1);
		notification.toggleClass('active');
		jQuery(notification.find('.data-user'))
				.text(data.user.name)
				.attr('uuid', data.user.uuid);
		jQuery(notification.find('.data-groupfrom'))
				.text(groupFrom.name)
				.attr('group-id', groupFrom.id)
				.css('--moderation-group-color', groupFrom.color);
		jQuery(notification.find('.data-groupto'))
				.text(groupTo.name)
				.attr('group-id', groupTo.id)
				.css('--moderation-group-color', groupTo.color);
	}
}

function kickModeratorDropHandler(event) {
	if (event.dataTransfer.types.includes("application/moderation-user")) {
		const data = JSON.parse(event.dataTransfer.getData("application/moderation-user"));
		const groupFrom = data.group;
		const notification = jQuery('#notification-kick');
		notification.attr('stage', 1);
		notification.toggleClass('active');
		jQuery(notification.find('.data-user'))
				.text(data.user.name)
				.attr('uuid', data.user.uuid);
		jQuery(notification.find('.data-groupfrom'))
				.text(groupFrom.name)
				.attr('group-id', groupFrom.id)
				.css('--moderation-group-color', groupFrom.color);
	}
}

function leaveModeratorDropHandler(event) {
	if (event.dataTransfer.types.includes("application/moderation-user")) {
		const data = JSON.parse(event.dataTransfer.getData("application/moderation-user"));
		const groupFrom = data.group;
		const notification = jQuery('#notification-leave');
		notification.attr('stage', 1);
		notification.toggleClass('active');
		jQuery(notification.find('.data-user'))
				.text(data.user.name)
				.attr('uuid', data.user.uuid);
		jQuery(notification.find('.data-groupfrom'))
				.text(groupFrom.name)
				.attr('group-id', groupFrom.id)
				.css('--moderation-group-color', groupFrom.color);
	}
}

function leaveModeratorDropHandler(event) {
	if (event.dataTransfer.types.includes("application/moderation-user")) {
		const data = JSON.parse(event.dataTransfer.getData("application/moderation-user"));
		const groupFrom = data.group;
		const notification = jQuery('#notification-leave');
		notification.attr('stage', 1);
		notification.toggleClass('active');
		jQuery(notification.find('.data-user'))
				.text(data.user.name)
				.attr('uuid', data.user.uuid);
		jQuery(notification.find('.data-groupfrom'))
				.text(groupFrom.name)
				.attr('group-id', groupFrom.id)
				.css('--moderation-group-color', groupFrom.color);
	}
}

function freezeModeratorDropHandler(event) {
	if (event.dataTransfer.types.includes("application/moderation-user")) {
		const data = JSON.parse(event.dataTransfer.getData("application/moderation-user"));
		const groupFrom = data.group;
		const frozen = parseInt(jQuery('#info-list-actions').attr('frozen'));
		const notification = jQuery('#notification-' + (frozen ? 'unfreeze' : 'freeze'));
		notification.attr('stage', 1);
		notification.toggleClass('active');
		jQuery(notification.find('.data-user'))
				.text(data.user.name)
				.attr('uuid', data.user.uuid);
		jQuery(notification.find('.data-groupfrom'))
				.text(groupFrom.name)
				.attr('group-id', groupFrom.id)
				.css('--moderation-group-color', groupFrom.color);
	}
}

async function addModeratorClickHandler(event) {
	const notification = jQuery('#notification-adduser');
	notification.attr('stage', 1);
	notification.toggleClass('active');
	notification.find('.data-user input.header-username-input').on('change', function(event) {
		const input = jQuery(event.target);
		const holder = input.parents('.header-username-input-holder');
		const suggestions = holder.children('.header-username-input-suggestions');
		const username = input.val().toLowerCase();
		const userEntry = suggestions.children('.header-username-input-suggestions-entry')
				.filter((_, x) => x.getAttribute('username').toLowerCase() === username);
		if (userEntry.length) {
			if (input.val() !== userEntry.attr('username')) {
				input.val(userEntry.attr('username'));
			}
		}
	}).on('input', function(event) {
		event.target.setCustomValidity("Nie ma takiego użytkownika");
	}).on('user-suggestion', function(event) {
		if (event.target.getAttribute('uuid')) {
			event.target.setCustomValidity("");
		}
	});
	await GroupSelectManager.get().updateGroupOptions(notification.find('.data-groupto > select'));
}

function editModeratorClickHandler(event) {
	const target = jQuery(event.currentTarget);
	const data = getModeratorFromButton(target);
	const groupFrom = data.group;
	const notification = jQuery('#notification-edituser');
	notification.toggleClass('active');
	mainCore.urlRequestGet(`/api/moderators/user.php?user-id=${encodeURIComponent(data.user.uuid)}&full`)
			.then(r => JSON.parse(r))
			.then(r => {
				console.log(r);
				notification.attr('stage', 1);
				const form = notification.find('.notification-all-panel-content-data-inputs form.view');
				console.log(r.accounts.discord);
				form.find('input[name="discord-id"]').attr('value', r.accounts.discord);
				form.find('input[name="forum-id"]').attr('value', r.accounts.forum);
				form.find('input[name="user-id"]').attr('value', data.user.uuid);
				jQuery(notification.find('.data-user'))
						.text(data.user.name)
						.attr('uuid', data.user.uuid);
				jQuery(notification.find('.data-group'))
						.text(groupFrom.name)
						.attr('group-id', groupFrom.id)
						.css('--moderation-group-color', groupFrom.color);
			});
}

function moderatorPromote(event) {
	const notification = jQuery('#notification-promotion');
	notification.attr('stage', 2);
	const uuid = jQuery(notification.find('.data-user')).attr('uuid');
	const groupId = jQuery(notification.find('.data-groupto')).attr('group-id');
	const comment = jQuery(notification.find('.data-comment > textarea')).val();
	mainCore.urlRequestGet(`../rest/moderators/promote.php?group-id=${groupId}&user-id=${encodeURIComponent(uuid)}&comment=${encodeURIComponent(comment)}`)
			.then(r => JSON.parse(r))
			.then(async r => {
				await ModerationBuilder.get().updateWholeModerationBoard();
				notification.attr('stage', 3);
				jQuery(notification.find('.data-user-result'))
						.text(r.user.name)
						.attr('uuid', r.user.uuid);
				jQuery(notification.find('.data-groupto-result'))
						.text(r.group_to.name)
						.attr('group-id', r.group_to.id)
						.css('--moderation-group-color', r.group_to.color);
			});
}

function moderatorDemote(event) {
	const notification = jQuery('#notification-demotion');
	notification.attr('stage', 2);
	const uuid = jQuery(notification.find('.data-user')).attr('uuid');
	const groupId = jQuery(notification.find('.data-groupto')).attr('group-id');
	const comment = jQuery(notification.find('.data-comment > textarea')).val();
	mainCore.urlRequestGet(`../rest/moderators/demote.php?group-id=${groupId}&user-id=${encodeURIComponent(uuid)}&comment=${encodeURIComponent(comment)}`)
			.then(r => JSON.parse(r))
			.then(async r => {
				await ModerationBuilder.get().updateWholeModerationBoard();
				notification.attr('stage', 3);
				jQuery(notification.find('.data-user-result'))
						.text(r.user.name)
						.attr('uuid', r.user.uuid);
				jQuery(notification.find('.data-groupto-result'))
						.text(r.group_to.name)
						.attr('group-id', r.group_to.id)
						.css('--moderation-group-color', r.group_to.color);
			});
}

function moderatorKick(event) {
	const notification = jQuery('#notification-kick');
	notification.attr('stage', 2);
	const uuid = jQuery(notification.find('.data-user')).attr('uuid');
	const comment = jQuery(notification.find('.data-comment > textarea')).val();
	mainCore.urlRequestGet(`../rest/moderators/leave.php?kick=true&user-id=${encodeURIComponent(uuid)}&comment=${encodeURIComponent(comment)}`)
			.then(r => JSON.parse(r))
			.then(async r => {
				await ModerationBuilder.get().updateWholeModerationBoard();
				notification.attr('stage', 3);
				jQuery(notification.find('.data-user-result'))
						.text(r.user.name)
						.attr('uuid', r.user.uuid);
			});
}

function moderatorLeave(event) {
	const notification = jQuery('#notification-leave');
	notification.attr('stage', 2);
	const uuid = jQuery(notification.find('.data-user')).attr('uuid');
	const comment = jQuery(notification.find('.data-comment > textarea')).val();
	mainCore.urlRequestGet(`../rest/moderators/leave.php?kick=false&user-id=${encodeURIComponent(uuid)}&comment=${encodeURIComponent(comment)}`)
			.then(r => JSON.parse(r))
			.then(async r => {
				await ModerationBuilder.get().updateWholeModerationBoard();
				notification.attr('stage', 3);
				jQuery(notification.find('.data-user-result'))
						.text(r.user.name)
						.attr('uuid', r.user.uuid);
			});
}

function moderatorFreeze(event) {
	const notification = jQuery('#notification-freeze');
	notification.attr('stage', 2);
	const uuid = jQuery(notification.find('.data-user')).attr('uuid');
	const comment = jQuery(notification.find('.data-comment > textarea')).val();
	mainCore.urlRequestGet(`../rest/moderators/freeze.php?action=freeze&user-id=${encodeURIComponent(uuid)}&comment=${encodeURIComponent(comment)}`)
			.then(r => JSON.parse(r))
			.then(async r => {
				await ModerationBuilder.get().updateWholeModerationBoard();
				notification.attr('stage', 3);
				jQuery(notification.find('.data-user-result'))
						.text(r.user.name)
						.attr('uuid', r.user.uuid);
			});
}

function moderatorUnfreeze(event) {
	const notification = jQuery('#notification-unfreeze');
	notification.attr('stage', 2);
	const uuid = jQuery(notification.find('.data-user')).attr('uuid');
	const comment = jQuery(notification.find('.data-comment > textarea')).val();
	mainCore.urlRequestGet(`../rest/moderators/freeze.php?action=unfreeze&user-id=${encodeURIComponent(uuid)}&comment=${encodeURIComponent(comment)}`)
			.then(r => JSON.parse(r))
			.then(async r => {
				await ModerationBuilder.get().updateWholeModerationBoard();
				notification.attr('stage', 3);
				jQuery(notification.find('.data-user-result'))
						.text(r.user.name)
						.attr('uuid', r.user.uuid);
			});
}

function moderatorAdduser(event) {
	event.preventDefault();
	const notification = jQuery('#notification-adduser');
	notification.attr('stage', 2);
	const values = event.target.elements;
	const uuid = values['user'].getAttribute('uuid');
	const groupId = values['group-id'].value;
	const comment = values['comment'].value;
	console.log([uuid, groupId, comment]);
	mainCore.urlRequestGet(`../rest/moderators/join.php?group-id=${groupId}&user-id=${encodeURIComponent(uuid)}&comment=${encodeURIComponent(comment)}`)
			.then(r => JSON.parse(r))
			.then(async r => {
				await ModerationBuilder.get().updateWholeModerationBoard();
				notification.attr('stage', 3);
				jQuery(notification.find('.data-user-result'))
						.text(r.user.name)
						.attr('uuid', r.user.uuid);
				jQuery(notification.find('.data-groupto-result'))
						.text(r.group_to.name)
						.attr('group-id', r.group_to.id)
						.css('--moderation-group-color', r.group_to.color);
			});
}

function moderatorEdituser(event) {
	event.preventDefault();
	const notification = jQuery('#notification-edituser');
	notification.attr('stage', 2);
	const values = event.target.elements;
	const uuid = values['user-id'].value;
	const discordId = values['discord-id'].value;
	const forumId = values['forum-id'].value;
	console.log([uuid, discordId, forumId]);
	mainCore.urlRequestGet(`../rest/moderators/edituser.php?user-id=${encodeURIComponent(uuid)}&discord-id=${encodeURIComponent(discordId)}&forum-id=${encodeURIComponent(forumId)}`)
			.then(r => JSON.parse(r))
			.then(async r => {
				notification.attr('stage', 3);
				jQuery(notification.find('.data-user-result'))
						.text(r.user.name)
						.attr('uuid', r.user.uuid);
				jQuery(notification.find('.data-discord-id-result'))
						.text(r.user.accounts.discord)
						.attr('discord-id', r.user.accounts.discord);
				jQuery(notification.find('.data-forum-id-result'))
						.text(r.user.accounts.forum)
						.attr('forum-id', r.user.accounts.forum);
			});
}


function enableFormModification(event) {
	event.preventDefault();
	const parent = jQuery(event.target).parents('.notification-all-panel-content-data-inputs');
	const view = parent.children('form.view')[0];
	const modify = parent.children('form.modify')[0];
	for (let i = 0; i < view.elements.length; i++) {
		const name = view.elements[i].name;
		if (name) {
			modify.elements[name].value = view.elements[i].value;
		}
	}
	parent.attr('modify', '');
}


function disableFormModification(event) {
	event.preventDefault();
	const parent = jQuery(event.target).parents('.notification-all-panel-content-data-inputs');
	const view = parent.children('form.view')[0];
	const modify = parent.children('form.modify')[0];
	for (let i = 0; i < view.elements.length; i++) {
		const name = view.elements[i].name;
		if (name) {
			modify.elements[name].value = '';
		}
	}
	parent.attr('modify', null);
}


class ModerationBuilder {

	static instance = null;

	groupTemplate = null;
	groupMemberTemplate = null;

	async updateWholeModerationBoard() {
		const board = await this.buildWholeModerationBoard();
		jQuery('#info-list-content-table').html(board);
	}

	async buildWholeModerationBoard() {
		return mainCore.urlRequestGet('/api/moderation/groups')
				.then(r => JSON.parse(r))
				.then(r => Promise.all(r.map(group => this.buildGroupBoard(group))))
				.then(async r => r.join(""));
	}

	async buildGroupBoard(group) {
	    return this.getOrLoadGroupTemplate()
				.then(async gtmpl => {
					const members = await this.buildGroupMembers(group);
					return mainCore.replaceTextInTemplate(gtmpl.replace('{{members}}', members), {
						'groupId': group.id,
						'groupName': group.name,
						'groupWeight': group.weight,
						'groupColor': group.color,
					});
				});
	}

	async buildGroupMembers(group) {
		return mainCore.urlRequestGet(`/api/moderation/users?groupId=${group.id}`)
				.then(r => JSON.parse(r))
				.then(r => Promise.all(r.map(member => this.buildGroupMember(member))))
				.then(r => r.join(''));
	}

	async buildGroupMember(member) {
	    return this.getOrLoadGroupMemberTemplate()
                .then(async mtmpl => {
                    const name = await mainCore.getUsername(member.uuid);
                    return mainCore.replaceTextInTemplate(mtmpl, {
						'userUuid': member.uuid,
						'userName': name,
						'userFrozen': member.frozen ? 1 : 0
					});
				});
	}

	async getOrLoadGroupTemplate() {
		// load group template or return if already loaded
		if (this.groupTemplate === null) {
			this.groupTemplate = mainCore.urlRequestGet('/templates/moderators/group.html')
		}
		return this.groupTemplate;
	}

	async getOrLoadGroupMemberTemplate() {
		// load group member template or return if already loaded
		if (this.groupMemberTemplate === null) {
			this.groupMemberTemplate = mainCore.urlRequestGet('/templates/moderators/group-member.html')
		}
		return this.groupMemberTemplate;
	}

	static get() {
		if (ModerationBuilder.instance == null) {
			ModerationBuilder.instance = new ModerationBuilder();
		}
		return ModerationBuilder.instance;
	}

}

class GroupSelectManager {

	static instance = null;

	optionTemplate = null;

	static get() {
		if (GroupSelectManager.instance == null) {
			GroupSelectManager.instance = new GroupSelectManager();
		}
		return GroupSelectManager.instance;
	}

	async updateGroupOptions(select) {
		const optionList = await mainCore.urlRequestGet(`../rest/moderators/groups.php`)
			.then(r => JSON.parse(r))
			.then(r => Promise.all(r.map(group => this.buildGroupOption(group))));
		this.clearCurrentOptions(select);
		optionList.forEach(el => {
			select.append(el);
		});
	}

	async buildGroupOption(group) {
		return this.getOrLoadOptionTemplate()
				.then(stmpl => {
					return mainCore.replaceTextInTemplate(stmpl, {
						'groupId': group.id,
						'groupName': group.name,
						'groupWeight': group.weight,
						'groupColor': group.color,
					});
				});
	}

	clearCurrentOptions(select) {
		select.children('.moderation-group-display').remove();
	}

	async getOrLoadOptionTemplate() {
		if (this.optionTemplate === null) {
			this.optionTemplate = mainCore.urlRequestGet('/templates/moderators/group-selector.html')
		}
		return this.optionTemplate;
	}

}

jQuery(document).ready(function() {
	ModerationBuilder.get().updateWholeModerationBoard();
});