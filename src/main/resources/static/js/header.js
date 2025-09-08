class UserSearchManager {

	static instance = null;

	suggestionTemplate = null;

	static get() {
		if (UserSearchManager.instance == null) {
			UserSearchManager.instance = new UserSearchManager();
		}
		return UserSearchManager.instance;
	}

	async updateUserSuggestions(input) {
		input.addClass('active');
		const parent = input.parent('.header-username-input-holder');
		const suggestions = parent.find('.header-username-input-suggestions');
		const subject = input.val();
		input.attr('uuid', '');
		let suggestionList = [];
		if (subject !== '') {
			suggestionList = await mainCore.urlRequestGet(`/api/auth/user?query=${encodeURIComponent(subject)}`)
				.then(r => JSON.parse(r))
				.then(r => Promise.all(r.map(user => this.buildUserSuggestion(user, subject))));
			
		}
		this.clearCurrentSuggestions(suggestions);
		suggestionList.forEach(el => {
			suggestions.append(el);
		});
		const userEntry = suggestions.children('.header-username-input-suggestions-entry')
				.filter((_, x) => x.getAttribute('username').toLowerCase() === subject.toLowerCase());
		input.attr('uuid', userEntry.attr('uuid'));
		input.trigger("user-suggestion");
		suggestions.children('.header-username-input-suggestions-entry').on('mousedown', function(ev) {
			const val = jQuery(ev.target).attr('username');
			if (val) {
				input.val(val);
				input.attr('uuid', jQuery(ev.target).attr('uuid'));
			}
		});
	}

	async buildUserSuggestion(user, subject) {
	    console.log(user);
		return this.getOrLoadSuggestionTemplate()
				.then(stmpl => {
					return mainCore.replaceTextInTemplate(stmpl, {
						'subject': user.subject,
						'ending': user.suffix,
						'uuid': user.user.uuid,
						'username': user.user.username
					});
				});
	}

	clearCurrentSuggestions(suggestions) {
		suggestions.children('.header-username-input-suggestions-entry').remove();
	}

	hideUserSuggestions(input) {
		input.removeClass('active');
		const parent = input.parent('.header-username-input-holder');
		const suggestions = parent.find('.header-username-input-suggestions');
		this.clearCurrentSuggestions(suggestions);
	}

	async getOrLoadSuggestionTemplate() {
		// load group template or return if already loaded
		if (this.suggestionTemplate === null) {
			this.suggestionTemplate = mainCore.urlRequestGet('../templates/user-search-entry.html')
		}
		return this.suggestionTemplate;
	}

}

jQuery(document).ready(function() {
	jQuery('input.header-username-input').on('input', function(event) {
		UserSearchManager.get().updateUserSuggestions(jQuery(event.target));
	}).on('focusout', function(event) {
		UserSearchManager.get().hideUserSuggestions(jQuery(event.target));
	});
	jQuery('input.header-username-input')
	        .map((_, e) => jQuery(e))
	        .map((_, e) => e.prop('form'))
});