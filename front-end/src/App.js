
import './App.css';

function App() {
  console.log("Hello World!");

  const reqBody = {
    username: "hello@gmail.com",
    password: "hello"
  }
fetch("api/v1/auth/login", {
  headers: {
    "Content-Type": "application/json"
  },
  method: "post",
  body: JSON.stringify(reqBody)
})
.then((response) => {
  return Promise.all([response.json(), response.headers]);
  // console.log(response.headers.get("Authorization"));
  // return response.json()
})
.then(([body, headers]) =>{
  const token = headers.get("Authorization")
   console.log(token)
   console.log(body);
  })

  return (
    <div className="App">
      <h1>Hello World</h1>
    </div>
  );
}

export default App;
