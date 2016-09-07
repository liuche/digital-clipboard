const {dom, flavor, rule, ruleset} = require('fathom-web');

/*
 * We're looking for Title, Synopsis, Poster Art, Ratings, and Source.
 */
// For a v1, we're only looking at IMDb. These rules will need to be generalized when we add other sites.

var rules = {
  title: ruleset(
    // Give any OpenGraph meta tag a score of 2, and tag it as title-ish as well:
    rule(dom('meta[property="og:title"]'),
      node => [{score: 2, flavor: 'titley', notes: node.element.content }]),

    // Give any title tag a score of 1, and tag it as title-ish:
    rule(dom('title'),
      node => [{score: 1, flavor: 'titley', notes: node.element.text }])
  ),
  description: ruleset(
    rule(dom('div[id="movieSynopsis"]'),
      node => [{score: 1, flavor: 'descriptiony', notes: node.element.textContent }])
  ),

  rating: ruleset(
    rule(dom('span[class="meter-value superPageFontColor"]'),
      node => [{score: 1, flavor: 'ratingy', notes: node.element.textContent }])
  ),

  poster: ruleset(
    rule(dom('img[class="posterImage"]'),
      node => [{score: 1, flavor: 'postery', notes: node.element.src }])
  ),

  site: ruleset(
    rule(dom('title'),
      node => [{score: 1, flavor: 'sitey', notes: "Rotten Tomatoes" }])
      // Rotten tomatoes doesn't have a title, so we'll hard code it for now.
  )
};

var res = {};
for (var flavor_property in rules) {
  try {
    var flavor_type = flavor_property + 'y';
    var node = rules[flavor_property].score(document).max(flavor_type);
    var property_value = node.flavors.get(flavor_type);
    res[flavor_property] = property_value;
  } catch (err) {
    console.log("Couldn't find " + flavor_property);
  }
}

java.handleParameters(JSON.stringify(res));
