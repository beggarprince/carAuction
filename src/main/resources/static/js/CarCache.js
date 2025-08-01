class CarCache{

    constructor() {

        this.cache = {
            cars: null,
            users: null,

        };

        //10 minutes
        this.cacheTimeout = 10 * 60 * 1000
        this.currentDisplayType = null;

    }

    clearCarTemplatesFromGrid(){
        const grid = document.getElementById('itemsGrid')
        const dataOutput = document.getElementById('dataOutput')

        if(grid) grid.innerHTML = '';

        if(dataOutput) dataOutput.innerHTML =''

        console.log("Cleared grid")
    }

    checkTimeout(time){
        if(!time) return false;

        return( Date.now() - timestamp) < this.cacheTimeout;
    }


}