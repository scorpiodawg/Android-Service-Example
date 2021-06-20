// ILookupService.aidl
package space.ravisu.example.lookupservice;

// Declare any non-default types here with import statements

parcelable QueryResult;

interface ILookupService {
    /** Request the process ID of this service */
    int getPid();

    /** Count of received connection requests from clients */
    int getConnectionCount();

    /** Perform a query and expect a response */
    QueryResult lookup(String key);
}