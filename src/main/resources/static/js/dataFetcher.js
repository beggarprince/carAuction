
const dataFetcher = {

        // Fetch cars data
        async fetchCars() {
            try {
                const fetchType = document.getElementById('fetchType').value;
                const response = await fetch(`/cars/getList?filter=${fetchType}`, {
                    method: 'POST'
                });

                if (!response.ok) throw new Error('Failed to fetch cars');

                const data = await response.json();
                appData.cars = data;
                console.log('Cars fetched:', data);
                return data;
            } catch (error) {
                console.error('Could not fetch cars:', error);
                return null;
            }
        },

        // Fetch users data
        async fetchUsers() {
            try {
                const response = await fetch('/api/users/getAllUsers', {
                    method: 'GET'
                });

                if (!response.ok) throw new Error('Failed to fetch users');

                const data = await response.json();
                appData.users = data;
                console.log('Users fetched:', data);
                return data;
            } catch (error) {
                console.error('Could not fetch users:', error);
                return null;
            }
        },

        // Fetch current user data
        async fetchCurrentUser() {
            const token = localStorage.getItem("jwt");
            if (!token) return null;

            try {
                let cleanToken = token.replace("Bearer", "").trim();
                const response = await fetch("/api/users/me", {
                    headers: { Authorization: `Bearer ${cleanToken}` }
                });

                if (!response.ok) throw new Error("Unauthorized");

                const userData = await response.json();
                appData.user = userData;

                const { name, username, id } = userData;

                localStorage.setItem("name", name);
                localStorage.setItem("username", username);
                localStorage.setItem("id", id);

                const welcomeEl = document.getElementById("welcome");
                if (welcomeEl) {
                    welcomeEl.textContent = `Welcome to the Circus of Value, ${name}, username being ${username}\nId:${id}`;
                }

                return userData;
            } catch (error) {
                console.error('Could not fetch user data:', error);
                return null;
            }
        },

        async fetchAllData() {
            //
            // const results = await Promise.allSettled([
            //     this.fetchCars(),
            //     this.fetchUsers(),
            //     this.fetchHomeData(),
            //     this.fetchCurrentUser()
            // ]);

            console.log('All data fetched:', appData);
            this.displayAllData();
            return appData;
        },

        displayAllData() {
            const template = document.getElementById('carStringTest');
            const dataOutput = document.getElementById('dataOutput');

            if (!template || !dataOutput) return;

            dataOutput.innerHTML = '';

            const clone = template.content.cloneNode(true);
            const dataDisplay = clone.getElementById('dataDisplay');

            if (dataDisplay) {
                dataDisplay.textContent = JSON.stringify(appData, null, 2);
            }

            dataOutput.appendChild(clone);
        },

        displayAllCars(){
            const template = document.getElementById('carStringTest');
            const dataOutput = document.getElementById('dataOutput');

            if (!template || !dataOutput) return;

            dataOutput.innerHTML ="";

            const clone = template.content.cloneNode(true);
            const dataDisplay = clone.getElementById('dataDisplay');

            if(dataDisplay){
                dataDisplay.textContent = JSON.stringify(appData.cars, null, 2);
            }
            dataOutput.appendChild(clone)
        },
        displayAllUsers(){
            const template = document.getElementById('carStringTest');
            const dataOutput = document.getElementById('dataOutput')

            if(!template || !dataOutput) return;

            dataOutput.innerHTML =""

            const clone = template.content.cloneNode(true)
            const dataDisplay = clone.getElementById('dataDisplay')

            if(dataDisplay){
                dataDisplay.textContent =  JSON.stringify(appData.users, null, 2);
            }
            dataOutput.appendChild(clone)

        },
    //This is where we build the cards
        displayAllAsCards(){

            const template = document.getElementById('carCardTemplate')
            const dataOutput = document.getElementById('dataOutput')

            if(!template || !dataOutput) return;

            dataOutput.innerHTML = ""

            appData.cars.forEach((car) => {


                const card = template.content.cloneNode(true)

                const cardTitle = card.getElementById('cardTitle')
                const cardPrice = card.getElementById('price')
                const cardImage = card.getElementById("cardImage")

                const year = car.year || "Unknown"
                const make = car.make || "Unknown"
                const model = car.model || "Unknown"
                const imageUrl = car.pictureUrl?.[0];
                let l = car.pictureUrl[0];

                const title = `${year} ${make} ${model}`
                const price = car.price || "Unknown"

                if (title) {
                    cardTitle.textContent = title
                }
                if (price) {
                    cardPrice.textContent = price
                }

                let mongoResponse ;

                if (imageUrl !== null) {
                    console.log("Attempting to fetch image")
                    mongoResponse = mongoDbFetchPicture(); // URL or n/a
                    if (mongoResponse !== "n/a") {
                        cardImage.src = mongoResponse;
                        cardImage.style.display = 'inline'
                    }
                }
                else{
                    console.log("No image associated with ar")
                }


                dataOutput.appendChild(card)
            })

        }

}

// public record CarDTO(
//     Long id,
//     String make,
//     String model,
//     int year,
//     int mileage,
//     double price,
//     String datePosted,
//     String ownerUsername,
//     Long user_id,
// List<String> pictureURL
// ) {
//
// }


//Example response
//     [
//     {
//         "id": 21,
//         "make": "Ford",
//         "model": "sad",
//         "year": 1998,
//         "mileage": 2000,
//         "price": 2000,
//         "datePosted": "2025-07-30 22:19:38.943",
//         "ownerUsername": null,
//         "user_id": null,
//         "picUrl": [
//             "688ae0ca88f9007092c81394"
//         ]
//     },
//         {
//             "id": 20,
//             "make": "Ford",
//             "model": "BroncoMK2",
//             "year": 5,
//             "mileage": 5,
//             "price": 5,
//             "datePosted": "2025-07-23 18:34:24.679",
//             "ownerUsername": null,
//             "user_id": null,
//             "picUrl": [
//                 "68817180ef732875df392662"
//             ]
//         },
    //Continued with more cars