window.addEvent('domready', function() {
	setTimeout('SetupAllFriendlyEditing();', 1);
});
function SetupAllFriendlyEditing() {
	SetupGeneralAssetTypeDescriptions();
}




















// Appropriate Asset type descriptions
function SetupGeneralAssetTypeDescriptions() {
	$$('.generalAssetType').each(function(field){
		field.addEvent('change', EGeneralAssetTypeChanged);
		SetAppropriateAssetTypeDescription(field);
	});
}
function EGeneralAssetTypeChanged(e) {
	SetAppropriateAssetTypeDescription(this);
}

function SetAppropriateAssetTypeDescription(node) {
	var ele = node.getParent().getParent().getElements('.descSpan')[0];
	if(!ele)
		return;
	switch (node.value) {
		case 'savings':
		case 'rrsp':
		case 'mutual fund':
			ele.innerHTML = 'Bank';
			break;
		case 'stocks':
		case 'bonds':
		case 'life insurance':
			ele.innerHTML = 'Institution';
			break;

		case 'gift':
			ele.innerHTML = 'From Whom / Description';
			break;

		default:
			ele.innerHTML = 'Description';
	}
}












function EFormSubmit(e) {
	e = new Event(e);

	if (this.submitting_already)
		e.stop();

	this.submitting_already = true;
}



















