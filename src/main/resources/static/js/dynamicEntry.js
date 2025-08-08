async function dynamicEntry(){
    //Build complex query

    //json
    // Initialize collections for each filter type
    const filters = {
        carType: [],
        make: [],
        model: null,
        transmission: [],
        drive: [],
        fuel: [],
        titleStatus: [],
        paintColor: [],
        carCondition: [],
        minPrice: null,
        maxPrice: null,
        minYear: null,
        maxYear: null,
        maxMileage: null
    };



    const dropdowns = document.querySelectorAll('[id^="drop-"]')

    //const applyFilterBtn = document.getElementById('applyFilters')
    //applyFilterBtn.addEventListener('click', async () => {

        //Loop through all dropdown filters
        //Does not include make, model, price, year
        dropdowns.forEach(dropdown => {
            let filterCategory = dropdown.id.replace('drop-', '')
            const checkedFilters = dropdown.querySelectorAll('input[id^="filter-"]:checked');

            if (checkedFilters.length > 0) {
                filters[filterCategory] =
                    Array.from(checkedFilters).map(checkbox => checkbox.value);
            }
        })

        const modelFilterValue = document.getElementById('model').value
        const makeFilterValue = document.getElementById('make')

        if(makeFilterValue) {
            let fuckJson = []
            fuckJson.push(makeFilterValue.value)
            filters["make"] = fuckJson;
        }

        if(modelFilterValue) filters["model"] = modelFilterValue;



        //const typeFilter = document.getElementById('vehicleType')
        const priceFilterMinValue = document.getElementById('minPrice').value
        const priceFilterMaxValue = document.getElementById('maxPrice').value

        if(priceFilterMinValue){
            filters["minPrice"] = priceFilterMinValue
        }
        if(priceFilterMaxValue){
            filters["maxPrice"] = priceFilterMaxValue;
        }
        const filterString = JSON.stringify(filters, null, 2);
        console.log(filterString)
        //We'll send to the controller which will just return ok afaik
        try{
            const response = await fetch('cars/advQuery', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json'},
                body: JSON.stringify(filters)
            })
            if(!response.ok) throw new Error('Error filtering')
            const r = await response.json()

            // console.log(r)
            appData.cars = r;
        }catch(e){

        }

    //})



}