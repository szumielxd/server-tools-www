class InfoAuth {

	constructor() {
		this.initListers();
	}

	initListers() {
            document.addEventListener('DOMContentLoaded', () => {
            	const self = this;
            	this.updateUserAuthInfo();
            	this.updateUserPortfelInfo();
            	jQuery('#notification').on('click', function(event) {
            		if (this === event.target && jQuery(this).attr('closeable') == 'true') {
            			self.showNotification(null, null);
            		}
            	});
            	jQuery('#info-auth-resetpassword').on('click', event => this.onResetPasswordButton());
            });
        }

	async updateUserAuthInfo() {
		const data = await jQuery.get(`/api/auth/user/${encodeURIComponent(document.body.dataset.userId)}`);
		const dateFormater = new Intl.DateTimeFormat(undefined, {timeStyle: "short", dateStyle: "short"});
		this.getValueElement("registration", "date").text(dateFormater.format(new Date(data.firstJoin.date)));
		this.getValueElement("registration", "ip").text(data.firstJoin.ip);
		this.getValueElement("login", "date").text(dateFormater.format(new Date(data.lastJoin.date)));
		this.getValueElement("login", "ip").text(data.lastJoin.ip);
		this.getValueElement("info", "name").text(data.username);
		this.getValueElement("info", "premium").attr("value", data.isPremium);
	}

	async updateUserPortfelInfo() {
		const data = await jQuery.get(`/api/portfel/user/${encodeURIComponent(document.body.dataset.userId)}`);
		this.getValueElement("portfel", "balance").text(data.balance + 'zł');
		this.getValueElement("portfel", "minorbalance").text('⧉' + data.minorBalance);
		this.getValueElement("portfel", "ignoretop").attr("value", data.ignoreTop);
	}

	getValueElement(section, type) {
		return jQuery(`.info-auth-content-table-section[type="${section}"] div[field="${type}"] > span.value`)
	}

	showNotification(title, content, closeable = true) {
		jQuery('#notification-header').text(title);
		jQuery('#notification-body').html(content);
		jQuery('#notification').attr('closeable', closeable == true);
	}

	async onResetPasswordButton() {
		const template = mainCore.replaceTextInTemplate(
				await jQuery.get('/templates/info/auth/reset-password/warning.html'),
				{
					username: document.body.dataset.userName,
					server: document.body.dataset.serverName
				});
		this.showNotification('Reset Hasła', template);
		jQuery('#info-auth-resetpassword-confirm').on('click', event => this.onResetPasswordConfirm())
	}

	async onResetPasswordConfirm() {
		let template = mainCore.replaceTextInTemplate(
				await jQuery.get('/templates/info/auth/reset-password/load.html'),
				{});
		this.showNotification('Reset Hasła', template, false);
		const password = await this.resetPassword();
		template = mainCore.replaceTextInTemplate(
        				await jQuery.get('/templates/info/auth/reset-password/success.html'),
        				{
        					username: document.body.dataset.userName,
        					password: password
        				});
        this.showNotification('Reset Hasła', template);
	}

	async resetPassword() {
		const password = this.randomPassword();
		await jQuery.ajax({
				url: `/api/auth/user/${encodeURIComponent(document.body.dataset.userId)}/change-password`,
				type: 'POST',
				contentType: "application/json",
				data: JSON.stringify({ newPassword: password })
			});
		return password;
	}

	randomPassword(length = 8) {
		const chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()+;,.?";
		let password = '';
		for (let i = 0; i < length; i++) {
			password += chars.charAt(Math.floor(Math.random() * chars.length));
		}
		return password;
	}

}
const infoAuth = new InfoAuth();