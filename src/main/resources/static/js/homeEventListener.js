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
    await dataFetcher.displayAllAsCards();
    await equalizeGridHeights();
});

document.getElementById('applyFilters').addEventListener('click', async() =>{
    await dynamicEntry();
})