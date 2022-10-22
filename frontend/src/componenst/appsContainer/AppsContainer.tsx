import React, { FC } from 'react';
import { useTranslation } from 'react-i18next';
import { Container, Row } from 'react-bootstrap';
import AppCard from '../../componenst/appCard';
import SearchField from '../../componenst/searchField';
import { App } from '../../models/services/app';

interface AppsContainerProps {
  data: App[] | undefined;
  setFilter: React.Dispatch<React.SetStateAction<string>>;
}

const AppsContainer: FC<AppsContainerProps> = ({ data, setFilter }) => {
  const { t } = useTranslation();
  return (
    <Container
      as="main"
      className="px-0 d-flex flex-column flex-wrap justify-content-start align-content-start"
    >
      <Row xs={5} className="w-100">
        <SearchField setFilter={setFilter} />
      </Row>
      <Row className="w-100">
        {data?.length ? (
          data?.map((item) => (
            <AppCard
              key={item.id}
              iconImage={item.iconImage}
              description={item.appDescription}
              appName={item.appName}
              id={item.id}
            />
          ))
        ) : (
          <p className="display-5 text-light">{t('appsFields.notFoundApps')}</p>
        )}
      </Row>
    </Container>
  );
};

export default AppsContainer;
