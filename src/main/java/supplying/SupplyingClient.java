package supplying;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class SupplyingClient {
	
    private static SupplyingServiceGrpc.SupplyingServiceBlockingStub blockingStub;
    private static SupplyingServiceGrpc.SupplyingServiceStub asyncStub;
    
	public static void main(String[] args) {
		
//		String host = "localhost";
//		
//		int port = 50051;
//		
//		ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
//														.usePlaintext()
//														.build();
//														
//													
//		
//		//retrieve the client stub
//		//blocking means that stub can be use for synchronous calls
//		SupplyingServiceBlockingStub blockingStub = SupplyingServiceGrpc.newBlockingStub(channel);
//		
//		SupplyRequest request = SupplyRequest.newBuilder().setUpdateStatus("Update status").build();
//		
//		SupplyResponse response = blockingStub.turnOnSupply(request);
//		
//		System.out.println("Server responding with "+response.getSupplyStatus());
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50053)
														.usePlaintext()
														.build();

		//stubs -- generate from proto
		blockingStub = SupplyingServiceGrpc.newBlockingStub(channel);
		asyncStub = SupplyingServiceGrpc.newStub(channel);
		
//		SupplyRequest request = SupplyRequest.newBuilder().setUpdateStatus("Update status").build();
//		SupplyResponse response = blockingStub.turnOnSupply(request);
//		System.out.println("Server responding with "+response.getSupplyStatus());
		
	}
	
		
	// TURN ON SUPPLY
    public static void turnOnSupply() throws io.grpc.StatusRuntimeException {
    	
    	SupplyRequest request = SupplyRequest.newBuilder().setUpdateStatus("Off").build();
    	SupplyResponse response = blockingStub.turnOnSupply(request);
    	System.out.print(response.getSupplyStatus());
        
    }

}
