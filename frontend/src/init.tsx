import i18next from 'i18next';
import React from 'react';
import { I18nextProvider, initReactI18next } from 'react-i18next';
import { Provider } from 'react-redux';
import App from './componenst/App';
import resources from './locales';
import { store } from './store';

export default async () => {
  const i18n = i18next.createInstance();

  await i18n.use(initReactI18next).init({
    resources,
    fallbackLng: 'ru',
  });

  return (
    <Provider store={store}>
      <I18nextProvider i18n={i18n}>
        <div className='store'>
          <App />
        </div>
      </I18nextProvider>
    </Provider>
  );
};
