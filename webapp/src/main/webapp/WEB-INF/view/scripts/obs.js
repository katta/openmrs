var obsCellFuncs = [
	function(data) { return "" + data.personName; },
	function(data) { 
		if (data.encounter == null || data.encounter == '') {
			return "";
		}
		return "<a href=\""+openmrsContextPath+"/admin/encounters/encounter.form?encounterId=" + data.encounter + "\">" + data.encounterName + " (" + data.encounterDate + ")</a>"; 
	},
	function(data) { return "" + data.conceptName; },
	function(data) { return "<a href=\"obs.form?obsId=" + data.obsId + "\">" + data.value + "</a>"; },
	function(data) { return "" + data.obsDate; }
];

var obsNoneCellFuncs = [
	function(data) { return "" + data; }
];

function showDiv(id) {
	var div = document.getElementById(id);
	if ( div ) {
		div.style.display = "";
	}
}

function hideDiv(id) {
	var div = document.getElementById(id);
	if ( div ) {
		div.style.display = "none";
	}
}

function showHideDiv(id) {
	var div = document.getElementById(id);
	if ( div ) {
		if ( div.style.display != "none" ) {
			div.style.display = "none";
		} else { 
			div.style.display = "";
		}
	}
}

var obsTableToRefresh = "";

function obsSearch( patientField, conceptField, encounterField, obsTable, obsDiv ) {
	showDiv(obsDiv);
	var patientId = patientField.length == 0 ? "" : dwr.util.getValue(patientField);
	var conceptId = conceptField.length == 0 ? "" : dwr.util.getValue(conceptField);
	var encounterId = encounterField.length == 0 ? "" : dwr.util.getValue(encounterField);
	obsTableToRefresh = obsTable;
	DWRObsService.getObsByPatientConceptEncounter(patientId, conceptId, encounterId, refreshObsTable);
}

function refreshObsTable(obss) {
	if ( document.getElementById(obsTableToRefresh) ) {
		dwr.util.removeAllRows(obsTableToRefresh);
		if ( obss && obss.length > 0 ) {
			dwr.util.addRows(obsTableToRefresh, obss, obsCellFuncs, {
				cellCreator:function(options) {
				    var td = document.createElement("td");
				    return td;
				},
				escapeHtml:false
			});
		} else {
			var noObsMsg = omsgs.noObsFound;
			var obsMsgs = [noObsMsg, ""];
			dwr.util.addRows(obsTableToRefresh, obsMsgs, obsNoneCellFuncs, {
				cellCreator:function(options) {
				    var td = document.createElement("td");
				    return td;
				},
				escapeHtml:false
			});
		}
	} else {
		alert('cannot find table called ' + obsTableToRefresh);
	}
}

function obsSearchClear( patientField, conceptField, encounterField ) {
	if ( patientField.length > 0 ) {
		dwr.util.setValue(patientField, "");
		var patPopup = dojo.widget.manager.getWidgetById("personId_selection");
		if ( patPopup ) {
			patPopup.displayNode.innerHTML = "";
			patPopup.setChangeButtonValue();
		}
	}
	if ( conceptField.length > 0 ) {
		dwr.util.setValue(conceptField, "");
		var conPopup = dojo.widget.manager.getWidgetById("conceptId_selection");
		if ( conPopup ) {
			conPopup.displayNode.innerHTML = "";
			conPopup.setChangeButtonValue();
		}
	}
	if ( encounterField.length > 0 ) {
		dwr.util.setValue(encounterField, "");
		var encPopup = dojo.widget.manager.getWidgetById("encounterId_selection");
		if ( encPopup ) {
			encPopup.displayNode.innerHTML = "";
			encPopup.setChangeButtonValue();
		}
	}
}