
let buttonLock = false;

const dataFetcher = {

        // Fetch cars data
        async fetchCars() {
            try {
                document.getElementById('')
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
    //TODO atm we fetch from the mongodb everytime, it should instead cache some images
        async displayAllAsCards() {

            if(buttonLock) return

            buttonLock = true;

            const template = document.getElementById('carCardTemplate')
            const dataOutput = document.getElementById('dataOutput')
            const grid = document.getElementById('itemsGrid')

            if (!template || !dataOutput) return;

            grid.innerHTML = ""
            dataOutput.innerHTML = ""

            for (const car of appData.cars) {


                const card = template.content.cloneNode(true)

               // console.log(car + "\n")
                const cardTitle = card.getElementById('cardTitle')
                const cardPrice = card.getElementById('cardPrice')
                const cardImage = card.getElementById("cardImage")
                const cardDescription = card.getElementById('cardDescription')
                const cardDatePosted = card.getElementById('datePosted')

                const year = car.year || "Unknown"
                const make = car.make || "Unknown"
                const model = car.model || "Unknown"
                const datePosted = timeAgoFormat(car.datePosted ) || "";

                //temp to differentiate
                const description = car.description || "";
                const pictureURL = car.pictureURL


                const title = `${year} ${make} ${model}`
                const price = car.price || "Unknown"

                if (title) {
                    cardTitle.textContent = title
                }
                if (price) {
                    cardPrice.textContent = "$" + price
                }

                if (pictureURL) {
                    let mongoResponse;
                    mongoResponse = await mongoDbFetchPicture(pictureURL); // URL or n/a

                    if (mongoResponse !== "n/a") {
                        cardImage.src = mongoResponse;
                       // cardImage.style.display = 'inline'
                    }
                } else {
                    console.log("No image associated with car: " + title)
                }

                if(description){
                    cardDescription.textContent = description;
                }
                if(datePosted){
                    cardDatePosted.textContent = datePosted;
                }

                dataOutput.appendChild(card)
            }

            buttonLock = false;
        }

}
