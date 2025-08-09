(async () =>{
    await dataFetcher.fetchCurrentUser();
})();

document.getElementById('fetchCars')
    .addEventListener('click', async () => {
    await dataFetcher.fetchCars();
    dataFetcher.displayAllData();
});

document.getElementById('fetchUsers').addEventListener('click', async () => {
    await dataFetcher.fetchUsers();
    dataFetcher.displayAllUsers();
});

document.getElementById('debugShowCar').addEventListener('click', () => {
    //console.log('Current app data:', appData);
    dataFetcher.displayAllCars();
});

document.getElementById('fetchAllData').addEventListener('click', async () => {
    await dataFetcher.fetchAllData();
});
document.getElementById('showCarGrid').addEventListener('click', async() =>{
    await dataFetcher.displayCarsAsGrid();
    await equalizeGridHeights();
});

    document.getElementById('applyFilters').addEventListener('click', async() =>{
        await dynamicEntry();
    })

    document.getElementById('applySort').addEventListener('click', async() => {

        const sortOption = document.getElementById('sortOptions').value

        // for(let i =0; i < appData.cars.length; i++){
        //     console.log(appData.cars[i].make)
        // }

        switch(sortOption) {
            case 'price-low-high':
                appData.cars.sort((a, b) => a.price - b.price);
                break;
            case 'price-high-low':
                appData.cars.sort((a, b) => b.price - a.price);
                break;
            case 'date-newest':
                appData.cars.sort((a, b) => new Date(b.datePosted) - new Date(a.datePosted));
                break;
            case 'date-oldest':
                appData.cars.sort((a, b) => new Date(a.datePosted) - new Date(b.datePosted));
                break;
            case 'mileage-low-high':
                appData.cars.sort((a, b) => a.mileage - b.mileage);
                break;
            case 'mileage-high-low':
                appData.cars.sort((a, b) => b.mileage - a.mileage);
                break;
            case 'year-newest':
                appData.cars.sort((a, b) => b.year - a.year);
                break;
            case 'year-oldest':
                appData.cars.sort((a, b) => a.year - b.year);
                break;
            default:
                console.log('Invalid input')
        }

        // for(let i =0; i < appData.cars.length; i++){
        //     console.log(appData.cars[i].make)
        // }
         await dataFetcher.displayCarsAsGrid()
         //await equalizeGridHeights();

    })


//misc functions for ui
//TODO some of these functions ought to be put in home.js, since some of the above could be reused

    function toggleCollapsible(header) {
        const parent = header;

        const arrow = header.querySelector('.collapsible-arrow');
        const content = header.nextElementSibling;

        // Toggle the expanded class
        arrow.classList.toggle('expanded');
        content.classList.toggle('expanded');

        //Signify the drop box has been clicked
        parent.classList.toggle('expanded')
    }

    // Reset drop down filters to unchecked
    const filterCheckboxes = document.querySelectorAll('input[id^="filter-"]');
    const resetFilterButton = document.getElementById('clearFilters')

    //Manual filters
    const makeFilter = document.getElementById('make')
    const modelFilter = document.getElementById('model')
    //const typeFilter = document.getElementById('vehicleType')
    const priceFilterMin = document.getElementById(
    'minPrice'
    )
    const priceFilterMax = document.getElementById('maxPrice')

    resetFilterButton.addEventListener('click', async() =>{

    filterCheckboxes.forEach(checkbox => {
        checkbox.checked = false;
    });

    //Reset the other filters
    makeFilter.value ="";
    modelFilter.value = ""
    //typeFilter.value=""
    priceFilterMax.value=""
    priceFilterMin.value =""
})

    //Hide all drop down filters
    //Reset? i can call the fun later i guess
    const boxes = document.querySelectorAll('div[id^="drop-"]');

    const hideDropButton = document.getElementById('collapseFilters')

    hideDropButton.addEventListener('click', async () =>{
    boxes.forEach( box =>{
            const header = box.querySelector('.collapsible-header')
            const arrow = box.querySelector('.collapsible-arrow')
            const content = box.querySelector('.collapsible-content')

            header.classList.remove('expanded');
            arrow.classList.remove('expanded');
            content.classList.remove('expanded')
        }
    )

})
