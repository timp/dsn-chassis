// ==UserScript==
// @name          Delete File
// @namespace     http://wwarn.org/userscripts
// @description	  Adds a delete button
// @include       */repository/curator/file-details*
// ==/UserScript==
// Notes:
//   * is a wildcard character
//   .tld is magic that matches all top-level domains (e.g. .com, .co.uk, .us, etc.)

	function findDownloadLink() {

		var links = document.getElementsByTagName('a');
		for(var i = 0; i < links.length; i++) {
			var link = links.item(i);
			if (link.innerHTML == 'Download' && link.href.indexOf('content') > 0) {
				return(link);
			}
        	}

	}

	function DeleteButton() {

		var url = window.location.href;
		this.base_url = url.substr(0, url.indexOf('&'));
		this.hostname = url.substr(7, url.indexOf('/', 8) - 7);
		this.add_buttons();
	}
	DeleteButton.prototype.add_buttons = function() {
		// Create the buttons
		var button1 = document.createElement('input');
		button1.className = 'deletesubmit';
		button1.style.marginLeft = '5px';
		button1.setAttribute('type', 'button');
		button1.value = 'Delete this file';
		button1.addEventListener('click', function() { deleteIt.start(); },
	true); 
		button1.setAttribute('id', 'delete_button1');
		// Add the buttons to the page
		var download = findDownloadLink();
		download.parentNode.insertBefore(button1, download);

	}


	DeleteButton.prototype.start = function() {
		var link = findDownloadLink();
		var comment = prompt('Please enter a comment', '');
     // OR var favorite = window.prompt('What is your favorite color?', 'RED');
		
     // if (comment) equivalent to if (comment != null && comment != "");
     if (comment) {

    // alert([comment,link.href].join('\n'));
	GM_xmlhttpRequest({
	  method:"DELETE",
	  url:link.href,
	  headers:{
	    "User-Agent":"monkeyagent",
	    "Accept":"application/atom+xml,application/xml,text/xml",
	    "X-Atom-Tombstone-Comment":comment,
	    },
		  onload:function(details) {
		    alert([      details.status,
		      details.statusText,
		      details.readyState,
		      details.responseHeaders,
		      details.responseText
		    ].join("\n"))
		  },
		  onerror:function(details) {
		    alert([      details.status,
		      details.statusText,
		      details.readyState,
		      details.responseHeaders,
		      details.responseText
		    ].join("\n"))
		  }
		});
	}
	}

	var deleteIt = new DeleteButton();
