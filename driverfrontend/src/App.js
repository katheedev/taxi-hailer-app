
import './App.css';
import { Switch, Route, Link,  BrowserRouter as Router,
} from "react-router-dom";
import Signup from "./Signup";
import LogIn from './LogIn';
import 'antd/dist/antd.css';
import Header from "../src/PublicLayout/Header";
import Home from "./Home";
import Trip from "./Trip";
import Edit from "./Edit"

function App() {
  return (
    <>

<Router>     
     <Switch>
     <Route exact path="/">
            <Header/>
d           <LogIn/>
          </Route>

          <Route exact path="/register">
            <Header/>
           <Signup/>
          </Route>

          <Route exact path="/log_in"> 
            <Header/>
           <LogIn/>
          </Route>

         <Route exact path="/home">
             <Header/>
             <Home/>
         </Route>
         <Route exact path="/trip">
             <Header/>
             <Trip/>
         </Route>

         <Route exact path="/edit">
             <Header/>
             <Edit/>
         </Route>


          <Route exact path="/sign_up"> 
            <Header/>
           <Signup/>
          </Route>
            </Switch>
   
    </Router>
    </>
  );
}

export default App;



