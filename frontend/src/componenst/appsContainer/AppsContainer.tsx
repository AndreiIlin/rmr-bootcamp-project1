import React, { FC } from 'react';
import { Container, Row } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { AiOutlineDoubleLeft, AiOutlineDoubleRight } from 'react-icons/ai';
import AppCard from '../../componenst/appCard';
import SearchField from '../../componenst/searchField';
import { App } from '../../models/services/app';

interface AppsContainerProps {
  data: Required<App>[] | undefined;
  setFilter: React.Dispatch<React.SetStateAction<string>>;
  page: number;
  setPage: React.Dispatch<React.SetStateAction<number>>;
}

const AppsContainer: FC<AppsContainerProps> = ({ data, setFilter, page, setPage }) => {
  const { t } = useTranslation();

  const handleNextPage = () => {
    if (data) {
      if (data?.length < 10) return;
      setPage((page) => page + 1);
    }
  };

  const handlePrevPage = () => {
    if (page <= 0) return;
    setPage((page) => page - 1);
  };

  return (
    <Container
      as="main"
      className="my-5 d-flex flex-column flex-wrap justify-content-start align-content-center"
    >
      {data?.length ? (
        <>
          <Row>
            <SearchField setFilter={setFilter} />
          </Row>
          <Row className="mt-3 align-items-center">
            <AiOutlineDoubleLeft
              className="col-1 h-100 text-light"
              opacity={page === 0 ? 0.5 : 1}
              style={{ cursor: 'pointer' }}
              onClick={handlePrevPage}
            />
            <div className="row col-10">
              {data.map((item) => (
                <AppCard
                  key={item.id}
                  iconImage={item.iconImage}
                  description={item.appDescription}
                  appName={item.appName}
                  id={item.id}
                />
              ))}
            </div>
            <AiOutlineDoubleRight
              className="col-1 h-100 text-light"
              opacity={data.length < 10 ? 0.5 : 1}
              style={{ cursor: 'pointer' }}
              onClick={handleNextPage}
            />
          </Row>
        </>
      ) : (
        <p className="display-5 text-light">{t('appsFields.notFoundApps')}</p>
      )}
    </Container>
  );
};

export default AppsContainer;
