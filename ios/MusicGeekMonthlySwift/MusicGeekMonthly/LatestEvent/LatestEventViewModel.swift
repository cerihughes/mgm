import UIKit

/// Domain-specific errors for the LatestEventViewModel
///
/// - noEvent: There is no latest event
enum LatestEventViewModelError: Error {
    case noEvent(String)
}

protocol LatestEventViewModel {
    /// The title to display in the UI
    var title: String {get}

    /// Any error / info message that needs to be rendered.
    var message: String? {get}

    /// Loads data. The completion block will be fired when data is available.
    func loadData(_ completion: @escaping () -> Void)

    /// The classic album (if loaded)
    var classicAlbumViewModel: LatestEventAlbumViewModel? {get}

    /// The new album (if loaded)
    var newAlbumViewModel: LatestEventAlbumViewModel? {get}
}

/// Default implementation of LatestEventViewModel
final class LatestEventViewModelImplementation: LatestEventViewModel {
    private let dataLoader: ViewModelDataLoader
    private let imageLoader: ImageLoader

    private var dataLoaderToken: DataLoaderToken? = nil

    var event: Event? = nil
    var message: String? = nil
    var classicAlbumViewModel: LatestEventAlbumViewModel? = nil
    var newAlbumViewModel: LatestEventAlbumViewModel? = nil

    let title = "Next Event"

    init(dataLoader: ViewModelDataLoader, imageLoader: ImageLoader) {
        self.dataLoader = dataLoader
        self.imageLoader = imageLoader
    }

    func loadData(_ completion: @escaping () -> Void) {
        if let dataLoaderToken = dataLoaderToken {
            dataLoaderToken.cancel()
        }

        dataLoaderToken = dataLoader.loadData() { [unowned self] (response) in
            DispatchQueue.main.async {
                self.dataLoaderToken = nil

                switch (response) {
                case .success(let events):
                    self.handleDataLoaderSuccess(events: events, completion)
                case .failure(let error):
                    self.handleDataLoaderFailure(error: error, completion)
                }
            }
        }
    }

    // MARK: Private

    private func handleDataLoaderSuccess(events: [Event], _ completion: () -> Void) {
        // Remove events without albums, then apply descending sort by ID
        let sortedEvents = events.filter { $0.classicAlbum != nil && $0.newAlbum != nil }.sorted { $0.number > $1.number }

        guard
            let event = sortedEvents.first,
            let classicAlbum = event.classicAlbum,
            let newAlbum = event.newAlbum
            else {
                let error = LatestEventViewModelError.noEvent("No events returned")
                handleDataLoaderFailure(error: error, completion)
                return
        }

        updateStateAndNotify(event: event, classicAlbum: classicAlbum, newAlbum: newAlbum, completion)
    }

    private func handleDataLoaderFailure(error: Error, _ completion: () -> Void) {
        self.event = nil
        self.classicAlbumViewModel = nil
        self.newAlbumViewModel = nil
        self.message = error.localizedDescription
        completion()
    }

    private func updateStateAndNotify(event: Event, classicAlbum: Album, newAlbum: Album, _ completion: () -> Void) {
        self.event = event
        self.classicAlbumViewModel = LatestEventAlbumViewModelImplementation(imageLoader: imageLoader, album: classicAlbum)
        self.newAlbumViewModel = LatestEventAlbumViewModelImplementation(imageLoader: imageLoader, album: newAlbum)
        self.message = nil
        completion()
    }
}