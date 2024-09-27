function formatBlogBody(str) {
	// Remove html tags
	var regExString = /(<([^>]+)>)/ig; //create reg ex and let it loop (g)
	console.log(str);
	var contentString = str // get text from node (no longer an object but string.

	contentString = contentString.replace(regExString, "") //find all tags and delete them.

	// Truncate body
	if (contentString.length > 165)
		contentString = contentString.substring(0, 165);
	contentString += "..."

	return contentString;
};

function fetchRSSFeed() {
	$.ajax({
		url: 'https://api.rss2json.com/v1/api.json',
		method: 'GET',
		dataType: 'json',
		data: {
			rss_url: 'https://boardgamegeek.com/rss/blog/1',
			api_key: 'yavrqia4uw2cf003zrswfrmoybe42ee9rr8qhrv0',
			count: 3
		}
	}).done(function (response) {
	if(response.status != 'ok'){ throw response.message; }
	
	console.log('====== ' + response.feed.title + ' ======');
	const year = new Date().getFullYear();

	for(var i in response.items){
		var item = response.items[i];
		// console.log(item);
		$('#blog-title-' + i).html(item.title);
		$('#blog-info-' + i).html(item.author + '  |  ' + item.pubDate.substring(0, 9));
		$('#blog-link-' + i).attr("href", item.link);
		$('#blog-title-link-' + i).attr("href", item.link);
		$('#blog-body-' + i).text(formatBlogBody(item.description));		
	}
	});
}

$(document).ready(function() {
	fetchRSSFeed();
 });
 

