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
