import { trueStoreApi } from '../trueStoreApi';
import { routes } from '../../../utils/routes';

interface Message {
  contractId: string;
  text: string;
}

const messagesApiSlice = trueStoreApi.injectEndpoints({
  endpoints: (build) => ({
    createMessage: build.mutation<Message, Message>({
      query: (message) => ({
        url: routes.api.messages(),
        method: 'POST',
        body: message,
      }),
      invalidatesTags: ['Message'],
    }),
    getMessageByContract: build.query<Message[], string>({
      query: (contractId) => routes.api.messagesByContract(contractId),
      providesTags: ['Message'],
    }),
    getMessageByApp: build.query<Message[], string>({
      query: (appId) => routes.api.messagesByApp(appId),
      providesTags: ['Message'],
    }),
  }),
});

export const { useCreateMessageMutation, useGetMessageByAppQuery, useGetMessageByContractQuery } =
  messagesApiSlice;
