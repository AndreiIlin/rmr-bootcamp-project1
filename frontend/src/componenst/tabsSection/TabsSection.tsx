/* eslint-disable camelcase */
import React, { FC } from 'react';
import { Tab, Tabs } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { App } from '../../models/services/app';
import ReportsContainer from '../reportsContainer';

interface TabsSectionProps {
  data: App | undefined;
}

const TabsSection: FC<TabsSectionProps> = ({ data }) => {
  const { t } = useTranslation();
  const { user_id } = JSON.parse(localStorage.getItem('trueStore') ?? '');
  const isOwner = user_id === data?.ownerId;

  return (
    <Tabs defaultActiveKey="description" className="mt-5" id="justify-tab-example" justify>
      <Tab eventKey="description" title={t('app.description')} className="mt-2 overflow-hidden">
        {data?.appDescription}
      </Tab>
      <Tab eventKey="comments" title={t('app.comments')} className="mt-3">
        Comments...
      </Tab>
      {(isOwner || data?.contractId) && (
        <Tab eventKey="reports" title={t('app.reports')} className="mt-3">
          <ReportsContainer isOwner={isOwner} appId={data?.id} contractId={data?.contractId} />
        </Tab>
      )}
    </Tabs>
  );
};

export default TabsSection;
