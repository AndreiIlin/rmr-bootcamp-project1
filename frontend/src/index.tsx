import 'bootstrap/dist/css/bootstrap.min.css';
import ReactDOM from 'react-dom/client';
import './index.css';
import init from './init';

const app = async () => {
  const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
  const vdom = await init();
  root.render(vdom);
};

app();
