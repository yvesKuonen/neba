/**
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 the "License";
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
$(function() {
	$("div.signal")
	.click(function() {
		var resultRowId = $(this).attr("class").split(" ")[1];
		var result = $('#' + resultRowId);
		if (result.data('opened')) {
			result.hide().data('opened', false);
		} else {
			result.show().data('opened', true);
		}
	}) 
	.css("cursor", "pointer")
	.attr("title", "Not run yet");
});

function run(testid, elem, rowId) {
	var signal = $('#signal' + rowId);
    signal.css("background-image", "url(selftests/static/loading.gif)");
	$.ajax({
		url : pluginRoot + '/run' + testid,
		dataType : 'json',
		success : function(data) {
			if (data.failed) {
				$('#result' + rowId).hide().remove();
				$('#row' + rowId).after($('<tr style="display:none;" id="result' + rowId + '"><td colspan="4"><h2>' + data.errorMsg + '</h2><br /><pre>' + data.trace + '</pre></td>'));
				signal.css('background-color', 'red');
				signal.attr('title', data.errorMsg);
			} else {
				$('#result' + rowId).hide().remove();
				signal.css('background-color', 'green');
				signal.attr('title', data.successMsg);
			}
            signal.css("background-image", "");
        }
	});
}

function runAll() {
	$("a.runlink").click();
}

$(function() {
    var $pluginTable = $("#plugin_table");

    $pluginTable.tablesorter();
    $pluginTable.find("td").hover(function(elem) {
        $(this).parent().children().css("background-color", "#c0c0c0");
    }, function(elem) {
        $(this).parent().children().css("background-color", "");
    });
});