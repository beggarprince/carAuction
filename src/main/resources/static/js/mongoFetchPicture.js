 const mongoDbFetchPicture = async (url) => {

  //  console.log("Downloading picture " + url)

    if (url === null || url === "undefined") {
      //  console.log("URL is null")
        return "n/a";
    }

    try {
        const mongoResponse = await fetch('/api/images/'
            + encodeURIComponent(url))

        if (!mongoResponse.ok) {
            return "n/a"
        }
     //   console.log("Returning pic")
        const blob = await mongoResponse.blob();

        let ll  = URL.createObjectURL(blob)

        return ll;
    }catch(err){
        console.log("Could not return image at " + url)
        return "n/a"
    }


};

// Call the function to execute it
mongoDbFetchPicture();