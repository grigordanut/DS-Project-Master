package supplying;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import supplying.SupplyingServiceGrpc.SupplyingServiceBlockingStub;
import supplying.SupplyingServiceGrpc.SupplyingServiceStub;

public class SupplyingClient {
	
	private static SupplyingServiceBlockingStub blockingStub;
	private static SupplyingServiceStub asyncStub;

	public static void main(String[] args) {
		
		ManagedChannel channel = ManagedChannelBuilder
						.forAddress("localhost", 50051)
						.usePlaintext()
						.build();
		
		//stubs -- generate from proto
		blockingStub = SupplyingServiceGrpc.newBlockingStub(channel);
		asyncStub = SupplyingServiceGrpc.newStub(channel);
		
		turnOnSupply();
		
	}
	
	//TURN ON SUPPLY
	public static void turnOnSupply() {
		
		String status = "On";
		SupplyRequest request = SupplyRequest.newBuilder()
							.setUpdateStatus(status)
							.build();
		SupplyResponse response = blockingStub.turnOnSupply(request);
		System.out.println("Server responded with: " + response.getSupplyStatus());
		
	}

}
