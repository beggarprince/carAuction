const uploadBtn = document.getElementById('uploadBtn');
const fileInput  = document.getElementById('imgInput');
const ownerName = document.getElementById('owner');

const idInput = document.getElementById('idInput');
const image = document.getElementById('image');
const downloadBtn = document.getElementById('btn');

let url;

uploadBtn.addEventListener('click', async () => {

    if (!fileInput.files.length) {
        alert('Choose a file first'); return;
    }

    const formData = new FormData();
    formData.append('file',  fileInput.files[0]);
    formData.append('owner', ownerName.value);

    try {
        const r  = await fetch('/api/images', {
            method: 'POST',
            body: formData });
        if (!r.ok) throw new Error(await r.text());
        const { id } = await r.json();
        console.log('Stored in Mongo with id=', id);

    } catch (err) {
        console.error('Upload failed:', err);
        alert(err.message);
    }
});

downloadBtn.addEventListener('click', async () => {
    console.log("Downloading picture " + idInput.value);
    const id = idInput.value.trim();

    if(!id) {
        alert('Id is empty');
        return;
    }
    try{
        console.log('Attempting to retrieve image');
        const r = await fetch ('/api/images/' + encodeURIComponent(id));

        if(!r.ok) throw new Error('Id not found');

        const blob = await r.blob();

        if(url) URL.revokeObjectURL(url);

        url = URL.createObjectURL(blob);
        image.src = url;
        image.style.display ='inline';
    }catch(err){
        alert('Fetch failed');
    }

})
