function getParams() {
	let url = window.location.href
	let params = url.substring(url.indexOf('?') + 1).split('&')
	return params;
}

export default getParams;
