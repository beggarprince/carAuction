//This script is to fetch a specific user meant to be used on the user page
//	It will get * listings the user owns, display pfp.
//		Maybe it will even allow changes if the user is you or something with the jwt token

//Using public username, don't expose user id
async function handleUserPage(){
	//Get user -> get cars
	
	//Made up button
	const btnEvent = document.getElementById('loadUserPagebtn');

	//Made up input		
	const uid = document.getElementById('uid');

	btnEvent.addEventListener('click', async() =>{
	//get user
	
	getSpecificUser(uid.value);

	//Since i know i want the user cars i don't need to collect form data
	

		//We do not use the userPage.user, that holds bio name and other information
		//this is their car listings
	try{
		const response = await fetch(`/api/cars/getAllFromUser=${uid}`,{
			method: 'GET'
		});
		if(!response.ok){
			throw new Error(`HTTP ${response.status}: ${response.statusText}`);
		}
			const responseJson = await response.json();
			//reusing the dashboard view code
			appData.cars = responseJson;
	}
		catch(error){
			if( error instanceof TypeError){
				console.error('Network Error:', error.message);
			}
			else if(error.message.includes('HTTP')){
				console.error('Server error:', error.message)
			}
			else{
				console.error('Unexpected error', error.message);
			}
		}
	})


}


async function getSpecificUser(user){
//with no user input i guess, just straight from the dom	
	try{
		//document.getElementById('uid');

		const response = await fetch(`/api/user/getUser=${user}`, 
			{
				method: 'GET'
			}
		);
		if(!response.ok){
			throw new Error(
				`HTTP ${response.status}: ${response.statusText}`);
		}
		const userJson = await response.json();

		userPage.user = userJson;
	}
	catch(error){
		if( error instanceof TypeError){
			console.error('Network error:', error.message);
		}
		else if(error.message.includes('HTTP')){
			console.error('Server Error:', error.message);
		}
		else{
			console.error('Unexpected error:', error.message);
		}
	}

}
