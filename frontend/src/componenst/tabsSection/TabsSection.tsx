/* eslint-disable camelcase */
import React, { FC } from 'react';
import { useTranslation } from 'react-i18next';
import { Tabs, Tab } from 'react-bootstrap';
import { App } from '../../models/services/app';
import { useGetAppReportsQuery } from '../../store/api/reportsApiSlice/reportsApiSlice';

interface TabsSectionProps {
  data: App | undefined;
}

const TabsSection: FC<TabsSectionProps> = ({ data }) => {
  const { t } = useTranslation();
  const { data: reportData } = useGetAppReportsQuery(data?.id as string);
  console.log(reportData);
  const { user_id } = JSON.parse(localStorage.getItem('trueStore') ?? '');
  return (
    <Tabs defaultActiveKey="description" className="mt-5" id="justify-tab-example" justify>
      <Tab eventKey="description" title={t('app.description')} className="mt-2 overflow-hidden">
        {data?.appDescription}
      </Tab>
      <Tab eventKey="comments" title={t('app.comments')} className="mt-3">
        Comments...
      </Tab>
      {data?.ownerId === user_id && (
        <Tab eventKey="reports" title={t('app.reports')} className="mt-3">
          {reportData?.map((report) => (
            <div key={report.contractId} className="d-flex justify-content-between">
              <p>{report.title}</p>
              <p>{report.reportStatus}</p>
            </div>
          ))}
        </Tab>
      )}
    </Tabs>
  );
};

export default TabsSection;
