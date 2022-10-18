import React from 'react';
import { Container } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import AppCard from '../../componenst/appCard';
import { useGetUserAppsQuery } from '../../store/api/appsApiSlice/appsApiSlice';

const UserAppsPage = () => {
  const { t } = useTranslation();
  const { data } = useGetUserAppsQuery({
    page: 0,
    size: 10,
    filter: '',
  });
  return data?.length ? (
    <Container
      fluid
      as='main'
      className='px-0 bg-main d-flex flex-row flex-wrap justify-content-start align-content-start vh-100 overflow-scroll'
    >
      {data?.map((item) => (
        <AppCard
          key={item.id}
          iconImage={item.iconImage}
          description={item.appDescription}
          appName={item.appName}
          id={item.id}
        />
      ))}
    </Container>
  ) : (
    <Container
      fluid
      as='main'
      className='px-0 bg-main d-flex flex-row flex-wrap justify-content-center align-content-center vh-100 overflow-scroll'
    >
      <p className='display-5 text-light'>{t('appsFields.notFoundApps')}</p>
    </Container>
  );
};

export default UserAppsPage;
