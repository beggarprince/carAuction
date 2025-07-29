//fetch quote from https://zenquotes.io/api/today
const dailyQuoteZen = {
    async fetchZenQuote(){
        try{
            console.log("Fetching daily quote")

            const response = await fetch('api/getQuote',
                {
                    method: 'GET'
                }
                );

            if(!response.ok) throw new Error ('Failed to fetch daily quote');

            const data = await response.text();

            appData.dailyQuote = data; //i don't need this
            const dailyQuoteElement = document.getElementById('quote-of-the-day')
            dailyQuoteElement.innerText = data;
            return data;
        }catch(error){
            console.error('Could not fetch daily quote: ' ,error)
            return null;
        }
    }

};

document.addEventListener('DOMContentLoaded', () => {
    (async () => {
        await dailyQuoteZen.fetchZenQuote();
    })();
})