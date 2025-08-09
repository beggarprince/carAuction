//Car upload
const response = document.getElementById('response');
const form = document.getElementById('carForm');

//Picture upload
const pictures  = document.getElementById('eImageInput');
const ownerName = document.getElementById('pictureOwner')

//Id of uploaded pictures to be sent with car
let pictureId = [];
let carId = [];


form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = localStorage.getItem("id")

    if(id===null){
        //TODO add a redirect or link
        console.log("Id is null please sign in")
        response.textContent = "Please sign in first";
        response.style.color = 'red';
        return
    }

    const body = {
        //Required fields
        make: document.getElementById("make").value,
        model: document.getElementById("model").value,
        year: document.getElementById("year").value,
        price: document.getElementById("price").value,
        mileage: document.getElementById("mileage").value,
        //TODO pass jwt token instead
        id: localStorage.getItem("id"),
        //Optional fields
        //ownerUsername:  localStorage.getItem("username"),
        transmission: document.getElementById("transmission").value,
        drive: document.getElementById("drive").value,
        fuel: document.getElementById("fuel").value,
        carType: document.getElementById("vehicleType").value,
        title: document.getElementById("title").value,
        cylinder: document.getElementById("cylinder").value,
        color: document.getElementById("paintColor").value,
        carCondition: document.getElementById("condition").value,
        description: document.getElementById("description").value
    }

    //Submit car
    console.log("Attempting to upload a car");
    response.textContent = "Uploading car listing...";
    response.style.color = 'blue';

    try{
        const res = await
            fetch('/uploadCar',{
                method: 'POST',
                headers: { 'Content-Type': 'application/json'},
                body: JSON.stringify(body)
            });

        if(res.ok){
            const data = await res.json();
            console.log("Car uploaded successfully")
            console.log(data)
            carId = data.id;
            response.textContent = `Successfully uploaded car (id ${carId})`;
            response.style.color = 'green';
        }
        else{
            const text = await res.text();
            console.log("Error in car upload response")
            console.log(text)
            response.textContent = text;
            response.style.color = 'red';
        }

    }catch(err){
        response.textContent = 'Error submitting car' + err.message;
        response.style.color = 'red';
    }

    console.log("Attempting to upload picture");
    const pictureFiles = pictures.files;

    //Upload pictures
    if(pictureFiles > 6 || pictureFiles === 0){
        alert("You can only upload 6 images and/or no images set")
        return;
    }

    response.textContent += " - Uploading photos...";


    const pictureMongoFormData = new FormData();
    for( const file of pictureFiles){
        pictureMongoFormData.append('file', file);
    }
    //TODO I need to change this later to use the jwt or local storage value instead of an input
    pictureMongoFormData.append('owner', ownerName.value)


    console.log("Attempting to link picture to car");
    //Atp we have the mongo form data ready to send
    try{
        const mongoResponse = await fetch('/api/images/multiple',{
            method: 'POST',
            body: pictureMongoFormData
        });
        if(!mongoResponse.ok){
            throw new Error(await mongoResponse.text())
        }
        console.log("Image uploaded to mongodb successfully")
        const mongoIdData = await mongoResponse.json();

        pictureId = mongoIdData.ids;

        //Link photos to car

        if(pictureId === null){
            console.log("pictureId is null")
        }
        if(carId === null){
            console.log("carId is null")
        }
        if(carId === null || pictureId === null){
            console.log("Aborting linking car to the mongodb")
            return
        }

        try{
            const pictureUpdateBody = {
                carId: carId,
                ids: pictureId
            }

            const updateWithPicsResponse = await fetch(
                '/cars/uploadPhotos', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(pictureUpdateBody)
                }
            );

            if(!updateWithPicsResponse.ok){
                throw new Error(await updateWithPicsResponse.text())
            }
            response.textContent = `Successfully uploaded car (id ${carId}) with ${pictureFiles.length} photos!`;
            response.style.color = 'green';

        }
        catch(err){
            console.error("Could not update car with pictures:", err);
            response.textContent += " - Photos uploaded but not linked to car";
            response.style.color = 'orange';
        }

    }catch(err){
        console.error('Photo upload failed:', err);
        response.textContent += " - Car uploaded but photo upload failed: " + err.message;
        response.style.color = 'orange';
    }


})
