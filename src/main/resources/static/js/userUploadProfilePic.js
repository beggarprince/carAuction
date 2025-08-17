
const picture = document.getElementById("pictureInput")
const ownerId = localStorage.getItem("id")

form.addEventListener('submit', async(e) =>{
    if(ownerId === null) return
    if(picture.files === null) return;

    const pictureFile =picture.files

    console.log("Attempting to add a user's picture")
    const pictureMongoFormData = new FormData()
    pictureMongoFormData.append('file', pictureFile)
    pictureMongoFormData.append('owner', ownerId.value)
    //Probably should add some console logs and if statement to show if inputs are valid
    //Or i could just respit the picture into a new html element + the ownerId

    console.log('Posting')
    try{
        const response = await fetch('/api/images/multiple',
            {
                method: 'POST',
                body: pictureMongoFormData
            });
        if(!response.ok){
            throw new Error(await response.text())
        }
        console.log("User profile picImage uploaded to mongodb successfully")
        const jsonResponse = await response.json()
        const pictureMongoId = jsonResponse.ids;
        if(pictureMongoId === null) {
            console.log('pictureId is null');
        }
        //Link this picture to the user in the db
        try{
            const updatedUserBody = {
                userId: ownerId,
                ids: pictureId
            }

            const userLinkResponse = await fetch ('/api/users/uploadUserPic',
                {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(updatedUserBody)
                });
            if(!userLinkResponse.ok){
                throw new Error (await userLinkResponse.text());
            }
            console.log(`Updated user ${ownerId}`)


        }catch(err){
            console.log('Could not link user profile picture to the user but pic is in db')
            console.err('Error: ', err)
        }

    }catch(err){
        console.error('Error: ", err')
    }

})