import UIKit

class ScoresCollectionViewCell: AlbumCollectionViewCell {
    let albumLabel = UILabel()
    let artistLabel = UILabel()
    let awardImageView = UIImageView()
    let ratingLabel = UILabel()
    let positionLabel = UILabel()

    private let spacing: CGFloat = 4.0

    override init(frame: CGRect) {
        super.init(frame: frame)

        albumLabel.translatesAutoresizingMaskIntoConstraints = false
        albumLabel.font = UIFont.systemFont(ofSize: 14)
        albumLabel.textColor = .black

        artistLabel.translatesAutoresizingMaskIntoConstraints = false
        artistLabel.font = UIFont.italicSystemFont(ofSize: 14)
        artistLabel.textColor = .black

        awardImageView.translatesAutoresizingMaskIntoConstraints = false
        awardImageView.contentMode = .scaleAspectFit

        ratingLabel.translatesAutoresizingMaskIntoConstraints = false
        ratingLabel.font = UIFont.boldSystemFont(ofSize: 19)

        positionLabel.translatesAutoresizingMaskIntoConstraints = false
        positionLabel.font = UIFont.boldSystemFont(ofSize: 14)
        positionLabel.textColor = .black
        positionLabel.textAlignment = .center

        addSubview(albumLabel)
        addSubview(artistLabel)
        addSubview(awardImageView)
        addSubview(ratingLabel)
        addSubview(positionLabel)

        var constraints: [NSLayoutConstraint] = []

        constraints.append(contentsOf: imageView.anchorTo(leadingAnchor: leadingAnchor, leadingConstant: spacing,
                                                          topAnchor: topAnchor, topConstant: spacing,
                                                          bottomAnchor: bottomAnchor, bottomConstant: -spacing,
                                                          widthAnchor: imageView.heightAnchor))

        let borderSpacing = spacing / 2.0
        apply(borderSpacing: borderSpacing)

        constraints.append(contentsOf: albumLabel.anchorTo(leadingAnchor: imageView.trailingAnchor, leadingConstant: spacing + borderSpacing,
                                                           trailingAnchor: awardImageView.leadingAnchor, trailingConstant: -spacing,
                                                           topAnchor: topAnchor, topConstant: spacing,
                                                           bottomAnchor: artistLabel.topAnchor, bottomConstant: -spacing))

        constraints.append(contentsOf: artistLabel.anchorTo(leadingAnchor: albumLabel.leadingAnchor,
                                                            trailingAnchor: albumLabel.trailingAnchor,
                                                            bottomAnchor: bottomAnchor, bottomConstant: -spacing,
                                                            heightAnchor: albumLabel.heightAnchor));

        constraints.append(contentsOf: awardImageView.anchorTo(trailingAnchor: trailingAnchor, trailingConstant: -spacing,
                                                               topAnchor: topAnchor, topConstant: spacing,
                                                               bottomAnchor: bottomAnchor, bottomConstant: -spacing,
                                                               widthAnchor: awardImageView.heightAnchor))

        constraints.append(contentsOf: ratingLabel.anchorTo(centerXAnchor: awardImageView.centerXAnchor,
                                                            centerYAnchor: awardImageView.centerYAnchor))

        constraints.append(contentsOf: positionLabel.anchorTo(topAnchor: topAnchor, topConstant: spacing,
                                                              centerXAnchor: awardImageView.centerXAnchor))
        constraints.append(positionLabel.heightAnchor.constraint(equalTo: awardImageView.heightAnchor, multiplier: 0.333))

        NSLayoutConstraint.activate(constraints)
    }

    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func prepareForReuse() {
        super.prepareForReuse()

        albumLabel.text = nil
        artistLabel.text = nil
        awardImageView.image = nil
        ratingLabel.text = nil
        ratingLabel.textColor = .black
        positionLabel.text = nil
    }
}