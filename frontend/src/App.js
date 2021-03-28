import "./App.css";
import MyRouter from "./routes/Router";
import {Provider} from "react-redux";
import {ConnectedRouter} from "connected-react-router";
import store, {history} from "./store";

const App = () => {
  return (
    <Provider store={store}>
      <ConnectedRouter history={history}>
        <MyRouter />
      </ConnectedRouter>
    </Provider>
  );
};

export default App;
