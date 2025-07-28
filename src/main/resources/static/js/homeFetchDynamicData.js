// Fetch home page data
const dynamicData = {
    async fetchHomeData() {
        try {
            console.log("Attempting to fetch dynamic data")
            const response = await fetch('/home-data');

            if (!response.ok) throw new Error('Failed to fetch home data');

            const data = await response.json();

            appData.homeData = data;

            // Update UI elements
            const textDiv = document.getElementById('tempMessage');
            if (textDiv && data.strings) {
                textDiv.innerText = data.strings.join(' ');
            }

            const image = document.getElementById('image');
            if (image && data.imgUrl) {
                image.src = data.imgUrl;
            }

            return data;

        } catch (error) {
            console.error('Could not fetch home data:', error);
            return null;
        }

    }

};


document.addEventListener('DOMContentLoaded', () => {
    (async () => {
        await dynamicData.fetchHomeData();
    })();
});