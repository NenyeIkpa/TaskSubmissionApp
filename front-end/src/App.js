
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Dashboard from './component/Dashboard';
import { Route, Routes } from 'react-router-dom';
import HomePage from './component/HomePage';
import Login from './component/Login';
import PrivateRoute from './PrivateRoute';
import TaskView from './component/TaskView';

function App() {  
  return (
    <Routes>
      <Route path="/dashboard" element = { 
      <PrivateRoute>
        <Dashboard />
        </PrivateRoute>} />
        <Route path='/tasks/:id' element = {
          <PrivateRoute>
            <TaskView />
          </PrivateRoute>
        } />
      <Route path="/" element = { <HomePage/> } />
      <Route path="/login" element = { <Login/> } />

    </Routes>
  );
}

export default App;
