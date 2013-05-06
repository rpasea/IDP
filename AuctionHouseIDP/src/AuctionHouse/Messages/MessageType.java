package AuctionHouse.Messages;

public enum MessageType {
	// Login
	Login,
	Logout,
	// Buyer
	LaunchAuction,
	DropAuction,
	AcceptOffer,
	RejectOffer,
	StartTransaction,
	// Seller
	MakeOffer,
	DropOffer,
	OfferExceed,
	OfferAccepted,
	OfferRefused
}
