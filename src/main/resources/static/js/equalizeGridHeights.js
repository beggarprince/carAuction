function equalizeGridHeights() {

    //console.log("Equalizing row height")

    const items = document.querySelectorAll('.item-card');
    let maxHeight = 0;

    // Reset heights first
    items.forEach(item => item.style.height = 'auto');

    // Find maximum height
    items.forEach(item => {
        const height = item.offsetHeight;
        if (height > maxHeight) {
            maxHeight = height;
        }
    });

    // Apply maximum height to all items
    items.forEach(item => {
        item.style.height = maxHeight + 'px';
    });
}
