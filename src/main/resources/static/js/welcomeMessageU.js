( async  () => {

    try{

        const id = localStorage.getItem("id")
        document.getElementById("welcome").textContent =
            `Welcome to the Circus of Value, ${id}`;
    } catch(err){
        console.error(err);
    }
})();