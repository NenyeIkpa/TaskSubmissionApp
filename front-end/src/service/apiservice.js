
function api (url, requestMethod, token, requestBody = null) {
    const fetchData = {
      headers: {
        'Content-Type': 'application/json'
      },
      method: requestMethod
    }

    if (token) {
      fetchData.headers.Authorization = `Bearer ${token}`;
    }

    if (requestBody) {
      fetchData.body = JSON.stringify(requestBody);
    }


   return fetch(url, fetchData).then((response) => {
    if(response.status === 200) return response.json()
})

}
export default api;