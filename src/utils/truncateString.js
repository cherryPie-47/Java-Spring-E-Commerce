function truncateString(string, stringLimit = 90) {
	if (string?.length > stringLimit) {
		return string.slice(0, stringLimit) + "...";
	}
	return string;
}

export default truncateString;
