// Get day difference (X days ago)
function timeAgoFormat(dateString) {
    const date = new Date(dateString.replace(' ', 'T'));
    const today = new Date();
    const diffInDays = Math.floor((today - date) / (1000 * 60 * 60 * 24));

    if (diffInDays === 0) return "Updated today";
    if (diffInDays === 1) return "1 day ago";
    return `${diffInDays} days ago`;
}

//Month, Day. Removes the excess in the Date function
function dateFormat(dateString) {
    const date = new Date(dateString.replace(' ', 'T'));
    const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
        'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

    return `${months[date.getMonth()]} ${date.getDate()}`;
}