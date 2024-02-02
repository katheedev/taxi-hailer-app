
import './App.css';
import { Switch, Route, Link,  BrowserRouter as Router,
} from "react-router-dom";
import Signup from "./Signup";
import LogIn from './LogIn';
import 'antd/dist/antd.css';
import Header from "../src/PublicLayout/Header";
import TripRequest from "./TripRequest";
import TripDetail from "./TripDetail";

function App() {
  return (
    <>

<Router>     
     <Switch>
     <Route exact path="/">
            <Header/>
           <Signup/>
           
          </Route>

          <Route exact path="/home">
            <Header/>
           <Signup/>
           
          </Route>
          <Route exact path="/log_in"> 
            <Header/>
           <LogIn/>
          </Route>
          <Route exact path="/sign_up"> 
            <Header/>
           <Signup/>
          </Route>

         <Route exact path="/trip_request">
             <Header/>
             <TripRequest/>
         </Route>

         <Route exact path="/trip_detail">
             <Header/>
             <TripDetail/>
         </Route>
     </Switch>
   
</Router>
    </>
  );
}

export default App;



