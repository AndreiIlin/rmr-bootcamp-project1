import React, { FC } from 'react';
import { Container } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useGetAppsQuery } from '../../../store/api/appsApiSlice/appsApiSlice';
import AppCard from '../../AppCard';

const MainPage: FC = () => {
  const { t } = useTranslation();
  const { data } = useGetAppsQuery({
    page: 0,
    size: 10,
    filter: '',
  });
  return (
    <Container
      fluid
      as='main'
      className='px-0 bg-indigo d-flex flex-row flex-wrap justify-content-start align-content-center vh-100 overflow-scroll'
    >
      {data?.length === 0 ? (
        <div>{t('appsFields.notFoundApps')}</div>
      ) : (
        data?.map((item) => (
          <AppCard
            key={item.id}
            iconImage={item.iconImage}
            description={item.appDescription}
            appName={item.appName}
            id={item.id}
          />
        ))
      )}
    </Container>
  );
};

export default MainPage;
