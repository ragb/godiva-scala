# Godiva's Scala Utilities

Godiva-scala is a set of utilities I've created during my scala development work which can be reused between projects.

Most of this code is blended from various projects, altough this library itself is not yet test covered in this project.

This is a work in progress so expect more code and documentation in the future.


### Modules

The project is composed of various modules, pick just the ones you need. Note that some of these depend on others.

* godiva-core: core classes, dependency of all other modules. Now just contains pagination definitions.
* godiva-slick: Slick Skafolding utilities and pagination support, along with primitive schema management.
* godiva-spray: spray utillities, now just contains pagination directives.
* godiva-akkha-http, same as above, ported to akka-http
* godiva-play-json: play json support, must for http serialisation.

### Dependencies


All modules are published to Bintray.

If you are using **SBT** you can add my Bintray repository as follows:

```

resolvers += Resolver.bintrayRepo("batista", "maven")
```


All artifacts are published to the **com.ruiandrebatista.godiva** organisation.

For instance, to depend on the **godiva-slick** module you'd do:

```

"com.ruiandrebatista.godiva" %% "godiva-slick" % godivaVersion
```