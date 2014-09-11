package bpi.most.opcua.server.handler;

import org.apache.log4j.Logger;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.core.CreateSubscriptionRequest;
import org.opcfoundation.ua.core.CreateSubscriptionResponse;
import org.opcfoundation.ua.core.DeleteSubscriptionsRequest;
import org.opcfoundation.ua.core.DeleteSubscriptionsResponse;
import org.opcfoundation.ua.core.ModifySubscriptionRequest;
import org.opcfoundation.ua.core.ModifySubscriptionResponse;
import org.opcfoundation.ua.core.PublishRequest;
import org.opcfoundation.ua.core.PublishResponse;
import org.opcfoundation.ua.core.RepublishRequest;
import org.opcfoundation.ua.core.RepublishResponse;
import org.opcfoundation.ua.core.SetPublishingModeRequest;
import org.opcfoundation.ua.core.SetPublishingModeResponse;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.core.SubscriptionServiceSetHandler;
import org.opcfoundation.ua.core.TransferSubscriptionsRequest;
import org.opcfoundation.ua.core.TransferSubscriptionsResponse;
import org.opcfoundation.ua.transport.EndpointServiceRequest;

import bpi.most.opcua.server.core.subscription.Subscription;

public class SubscriptionServiceHandler extends ServiceHandlerBase implements SubscriptionServiceSetHandler {
	
	private static final Logger LOG = Logger
			.getLogger(SubscriptionServiceHandler.class);
	
	@Override
	public void onCreateSubscription(EndpointServiceRequest<CreateSubscriptionRequest, CreateSubscriptionResponse> serviceReq) throws ServiceFaultException {
		
		LOG.info("onCreateSubscription");
		
		initRequestContext(serviceReq);
		CreateSubscriptionRequest req = serviceReq.getRequest();
		CreateSubscriptionResponse resp = new CreateSubscriptionResponse();
		
		LOG.info("request: " + req.toString());
		resp.setResponseHeader(buildRespHeader(req));
		
		Subscription subscription = getSubscriptionManager().createSubscription(req);
		
		resp.setSubscriptionId(new UnsignedInteger(subscription.getId()));
		resp.setRevisedLifetimeCount(new UnsignedInteger(subscription.getLifetimeCount()));
		resp.setRevisedMaxKeepAliveCount(new UnsignedInteger(subscription.getMaxKeepAliveCount()));
		resp.setRevisedPublishingInterval(subscription.getPublishingInterval());
		
		resp.setResponseHeader(buildRespHeader(req));
		sendResp(serviceReq, resp);
	}

	@Override
	public void onModifySubscription(EndpointServiceRequest<ModifySubscriptionRequest, ModifySubscriptionResponse> serviceReq) throws ServiceFaultException {
		initRequestContext(serviceReq);
		ModifySubscriptionRequest req = serviceReq.getRequest();
		ModifySubscriptionResponse resp = new ModifySubscriptionResponse();
		
		resp.setResponseHeader(buildErrRespHeader(req, StatusCodes.Bad_ServiceUnsupported));
		sendResp(serviceReq, resp);
	}

	@Override
	public void onSetPublishingMode(EndpointServiceRequest<SetPublishingModeRequest, SetPublishingModeResponse> serviceReq) throws ServiceFaultException {
		initRequestContext(serviceReq);
		SetPublishingModeRequest req = serviceReq.getRequest();
		SetPublishingModeResponse resp = new SetPublishingModeResponse();
		
		getSubscriptionManager().setPublishingMode(req);
		
		resp.setResponseHeader(buildRespHeader(req));
		sendResp(serviceReq, resp);
	}

	@Override
	public void onPublish(EndpointServiceRequest<PublishRequest, PublishResponse> serviceReq) throws ServiceFaultException {
		initRequestContext(serviceReq);
		PublishRequest req = serviceReq.getRequest();
		PublishResponse resp = new PublishResponse();
		
		resp.setResponseHeader(buildErrRespHeader(req, StatusCodes.Bad_ServiceUnsupported));
		sendResp(serviceReq, resp);
	}

	@Override
	public void onRepublish(EndpointServiceRequest<RepublishRequest, RepublishResponse> serviceReq) throws ServiceFaultException {
		initRequestContext(serviceReq);
		RepublishRequest req = serviceReq.getRequest();
		RepublishResponse resp = new RepublishResponse();
		
		resp.setResponseHeader(buildErrRespHeader(req, StatusCodes.Bad_ServiceUnsupported));
		sendResp(serviceReq, resp);
	}

	@Override
	public void onTransferSubscriptions(EndpointServiceRequest<TransferSubscriptionsRequest, TransferSubscriptionsResponse> serviceReq) throws ServiceFaultException {
		initRequestContext(serviceReq);
		TransferSubscriptionsRequest req = serviceReq.getRequest();
		TransferSubscriptionsResponse resp = new TransferSubscriptionsResponse();
		
		resp.setResponseHeader(buildErrRespHeader(req, StatusCodes.Bad_ServiceUnsupported));
		sendResp(serviceReq, resp);
	}

	@Override
	public void onDeleteSubscriptions(EndpointServiceRequest<DeleteSubscriptionsRequest, DeleteSubscriptionsResponse> serviceReq) throws ServiceFaultException {
		initRequestContext(serviceReq);
		DeleteSubscriptionsRequest req = serviceReq.getRequest();
		DeleteSubscriptionsResponse resp = new DeleteSubscriptionsResponse();
		
		getSubscriptionManager().deleteSubscription(req);
		
		resp.setResponseHeader(buildRespHeader(req));
		sendResp(serviceReq, resp);
	}

}
