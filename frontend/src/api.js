const API = "http://localhost:8080";


export async function shortenUrl(url){

    const response = await fetch(
        `${API}/api/shorten`,
        {
            method:"POST",

            headers:{
                "Content-Type":"application/json"
            },

            body:JSON.stringify({
                originalUrl:url
            })
        }
    );


    const data = await response.json();

    console.log("BACKEND RESPONSE:", data);


    if(!response.ok){
        throw new Error(JSON.stringify(data));
    }


    return data;
}