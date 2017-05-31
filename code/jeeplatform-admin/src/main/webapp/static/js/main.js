;
$(function() {

	function addTab(title, url) {
		if ($('#tt').tabs('exists', title)) {
			$('#tt').tabs('select', title);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'
					+ url + '" style="width:100%;height:100%;"></iframe>';
			$('#tt').tabs(
					'add',
					{
						title : title,
						content : content,
						closable : true,
						tools : [{
							iconCls : 'icon-mini-refresh',
							handler : function() {
								var selectTab = $('#tt').tabs('getSelected');

								var url = $(selectTab.panel('options').content)
										.attr('src');
								$('#tt').tabs('update', {
									tab : selectTab,
									options : {
										href : url
									}
								});

							}
						}],
						style : {
							'padding' : '5px',
							'border-width' : 0,

						}
					});
		}
	}

	$('#west ul a').click(function() {
		var title = $(this).text();
		var url = $(this).attr('href');

		addTab(title, url);
		return false;
	});

	// $('#logout').click(function() {
	// // $.get('/myweb/api/user/logout');
	// $.ajax({
	// url : '/myweb/api/user/logout',
	// async : false,
	// type : 'GET'
	// });
	// alert("logout");
	// window.location.href = '/myweb/api/user/login.page';
	// });

});